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

import net.ymate.platform.core.YMP;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午1:22
 * @version 1.0
 */
public interface IMailSender {

    String MODULE_NAME = "module.mailsender";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    IMailSenderModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * @return 返回邮件模板管理器实例
     */
    IMailTemplateManager getTemplateManager();

    /**
     * @return 创建邮件发送构建工具实例
     */
    IMailSendBuilder create();

    /**
     * @param serverName 服务名称
     * @return 创建指定服务名称的邮件发送构建工具实例
     */
    IMailSendBuilder create(String serverName);

    /**
     * 邮件文档类型
     */
    enum MimeType {

        TEXT_PLAIN("text/plain"),
        TEXT_HTML("text/html");

        private String mimeType;

        MimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getMimeType() {
            return mimeType;
        }
    }

    /**
     * 邮件发送等级
     */
    enum Level {

        LEVEL_NORMAL(0),
        LEVEL_HIGH(1),
        LEVEL_LOW(2);

        private int level;

        Level(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
