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
package net.ymate.module.mailsender;

import net.ymate.module.mailsender.impl.DefaultModuleCfg;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午1:39
 * @version 1.0
 */
@Module
public class MailSender implements IModule, IMailSender {

    private static final Log _LOG = LogFactory.getLog(MailSender.class);

    public static final Version VERSION = new Version(1, 0, 0, MailSender.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile IMailSender __instance;

    private YMP __owner;

    private IMailSenderModuleCfg __moduleCfg;

    private boolean __inited;

    public static IMailSender get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(MailSender.class);
                }
            }
        }
        return __instance;
    }

    public String getName() {
        return IMailSender.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-module-mailsender-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            //
            __moduleCfg.getMailSendProvider().init(this);
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public IMailSendBuilder create() {
        return __moduleCfg.getMailSendProvider().create();
    }

    public IMailSendBuilder create(String serverName) {
        return __moduleCfg.getMailSendProvider().create(__moduleCfg.getMailSendServerCfg(serverName));
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            __moduleCfg.getMailSendProvider().destroy();
            //
            __moduleCfg = null;
            __owner = null;
        }
    }

    public YMP getOwner() {
        return __owner;
    }

    public IMailSenderModuleCfg getModuleCfg() {
        return __moduleCfg;
    }
}
