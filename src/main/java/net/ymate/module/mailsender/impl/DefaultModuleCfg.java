/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.module.mailsender.impl;

import net.ymate.module.mailsender.IMailSendProvider;
import net.ymate.module.mailsender.IMailSender;
import net.ymate.module.mailsender.IMailSenderModuleCfg;
import net.ymate.module.mailsender.MailSendServerCfgMeta;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.support.IPasswordProcessor;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午1:42
 * @version 1.0
 */
public class DefaultModuleCfg implements IMailSenderModuleCfg {

    private IMailSendProvider __mailSendProvider;

    private int __threadPoolSize;

    private String __defaultServerName;

    private String __templatePath;

    private Map<String, MailSendServerCfgMeta> __mailSendServerCfgs;

    @SuppressWarnings("unchecked")
    public DefaultModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IMailSender.MODULE_NAME);
        //
        if ((__mailSendProvider = ClassUtils.impl(_moduleCfgs.get("provider_class"), IMailSendProvider.class, this.getClass())) == null) {
            __mailSendProvider = new DefaultMailSendProvider();
        }
        //
        __threadPoolSize = BlurObject.bind(_moduleCfgs.get("thread_pool_size")).toIntValue();
        if (__threadPoolSize <= 0) {
            __threadPoolSize = Runtime.getRuntime().availableProcessors();
        }
        //
        __defaultServerName = StringUtils.defaultIfBlank(_moduleCfgs.get("default_server_name"), "default");
        //
        __templatePath = RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(_moduleCfgs.get("template_path"), "${root}/mail_templates/"));
        //
        String _defaultDisplayName = StringUtils.trimToNull(_moduleCfgs.get("default_display_name"));
        String _defaultFromAddr = StringUtils.trimToNull(_moduleCfgs.get("default_from_addr"));
        boolean _debugEnabled = BlurObject.bind(_moduleCfgs.get("debug_enabled")).toBooleanValue();
        //
        __mailSendServerCfgs = new HashMap<String, MailSendServerCfgMeta>();
        String _serverNameStr = StringUtils.defaultIfBlank(_moduleCfgs.get("server_name_list"), "default");
        if (StringUtils.contains(_serverNameStr, __defaultServerName)) {
            String[] _serverNameList = StringUtils.split(_serverNameStr, "|");
            for (String _serverName : _serverNameList) {
                String _prefix = "server.".concat(_serverName);
                boolean _needAuth = BlurObject.bind(_moduleCfgs.get(_prefix.concat(".need_auth"))).toBooleanValue();
                boolean _tlsEnabled = BlurObject.bind(_moduleCfgs.get(_prefix.concat(".tls_enabled"))).toBooleanValue();
                boolean _sslEnabled = BlurObject.bind(_moduleCfgs.get(_prefix.concat(".ssl_enabled"))).toBooleanValue();
                MailSendServerCfgMeta _meta;
                if (_needAuth) {
                    String _smtpPassword = _moduleCfgs.get(_prefix.concat(".smtp_password"));
                    if (BlurObject.bind(_moduleCfgs.get(_prefix.concat(".password_encrypted"))).toBooleanValue()) {
                        IPasswordProcessor _processor = ClassUtils.impl(_moduleCfgs.get(_prefix.concat(".password_class")), IPasswordProcessor.class, this.getClass());
                        if (_processor == null) {
                            _processor = owner.getConfig().getDefaultPasswordClass().newInstance();
                        }
                        _smtpPassword = _processor.decrypt(_smtpPassword);
                    }
                    _meta = new MailSendServerCfgMeta(_serverName, _moduleCfgs.get(_prefix.concat(".smtp_host")), _tlsEnabled, _sslEnabled, _moduleCfgs.get(_prefix.concat(".smtp_username")), _smtpPassword);
                } else {
                    _meta = new MailSendServerCfgMeta(_serverName, _moduleCfgs.get(_prefix.concat(".smtp_host")), _tlsEnabled, _sslEnabled);
                }
                _meta.setDebugEnabled(_debugEnabled);
                _meta.setDisplayName(StringUtils.defaultIfBlank(_moduleCfgs.get(_prefix.concat(".display_name")), _defaultDisplayName));
                _meta.setFromAddr(StringUtils.defaultIfBlank(_moduleCfgs.get(_prefix.concat(".from_addr")), _defaultFromAddr));
                //
                String smtpPort = _moduleCfgs.get(_prefix.concat(".smtp_port"));
                if (StringUtils.isNotBlank(smtpPort) && StringUtils.isNumeric(smtpPort)) {
                    _meta.setSmtpPort(BlurObject.bind(smtpPort).toIntValue());
                }
                if (_sslEnabled) {
                    _meta.setSocketFactoryClassName(StringUtils.defaultIfBlank(_moduleCfgs.get(_prefix.concat(".socket_factory_class")), "javax.net.ssl.SSLSocketFactory"));
                    _meta.setSocketFactoryFallback(BlurObject.bind(_moduleCfgs.get(_prefix.concat(".socket_factory_fallback"))).toBooleanValue());
                }
                __mailSendServerCfgs.put(_serverName, _meta);
            }
        } else {
            throw new IllegalArgumentException("The default mail server name does not match");
        }
    }

    @Override
    public IMailSendProvider getMailSendProvider() {
        return __mailSendProvider;
    }

    @Override
    public int getThreadPoolSize() {
        return __threadPoolSize;
    }

    @Override
    public String getDefaultServerName() {
        return __defaultServerName;
    }

    @Override
    public String getTemplatePath() {
        return __templatePath;
    }

    @Override
    public Map<String, MailSendServerCfgMeta> getMailSendServerCfgs() {
        return Collections.unmodifiableMap(__mailSendServerCfgs);
    }

    @Override
    public MailSendServerCfgMeta getDefaultMailSendServerCfg() {
        return __mailSendServerCfgs.get(__defaultServerName);
    }

    @Override
    public MailSendServerCfgMeta getMailSendServerCfg(String name) {
        return __mailSendServerCfgs.get(name);
    }
}
