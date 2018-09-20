/*
 * Copyright 2007-2018 the original author or authors.
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

import freemarker.template.Configuration;
import net.ymate.module.mailsender.IMailSender;
import net.ymate.module.mailsender.IMailTemplateManager;
import net.ymate.platform.core.i18n.I18N;
import net.ymate.platform.core.support.FreemarkerConfigBuilder;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 邮件模板管理器接口默认实现
 *
 * @author 刘镇 (suninformation@163.com) on 2018/9/19 下午6:26
 * @version 1.0
 */
public class DefaultMailTemplateManager implements IMailTemplateManager {

    private static final Log _LOG = LogFactory.getLog(DefaultMailTemplateManager.class);

    private Configuration freemarkerConfig;

    public DefaultMailTemplateManager(IMailSender owner) throws IOException {
        freemarkerConfig = FreemarkerConfigBuilder.create().addTemplateFileDir(new File(owner.getModuleCfg().getTemplatePath())).build();
    }

    @Override
    public String compileTemplate(String templateName, Map<String, Object> attributes) {
        String _returnValue = null;
        try {
            if (!templateName.endsWith(".ftl")) {
                templateName += ".ftl";
            }
            StringBuilderWriter _writer = new StringBuilderWriter();
            freemarkerConfig.getTemplate(templateName, I18N.current()).process(attributes, _writer);
            //
            _returnValue = _writer.toString();
        } catch (Exception e) {
            _LOG.warn("An exception occurred when compiling template " + templateName + ": ", RuntimeUtils.unwrapThrow(e));
        }
        return _returnValue;
    }
}
