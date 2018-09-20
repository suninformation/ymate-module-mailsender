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

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午1:38
 * @version 1.0
 */
public interface IMailSenderModuleCfg {

    /**
     * @return 邮件发送服务提供者类, 默认值: net.ymate.module.mailsender.impl.DefaultMailSendProvider
     */
    IMailSendProvider getMailSendProvider();

    /**
     * @return 发送线程池初始化大小, 默认值: Runtime.getRuntime().availableProcessors()
     */
    int getThreadPoolSize();

    /**
     * @return 默认邮件发送服务名称, 默认值: default
     */
    String getDefaultServerName();

    /**
     * @return 模板文件路径, 默认值: ${root}/mail_templates/
     */
    String getTemplatePath();

    /**
     * @return 返回邮件发送服务配置映射
     */
    Map<String, MailSendServerCfgMeta> getMailSendServerCfgs();

    /**
     * @return 返回默认邮件发送服务配置
     */
    MailSendServerCfgMeta getDefaultMailSendServerCfg();

    /**
     * @param name 邮件发送服务名称
     * @return 返回指定名称的邮件发送服务配置
     */
    MailSendServerCfgMeta getMailSendServerCfg(String name);
}
