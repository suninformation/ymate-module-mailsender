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
package net.ymate.module.mailsender;

import java.util.Map;

/**
 * 邮件模板管理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 2018/9/19 下午6:25
 * @version 1.0
 */
public interface IMailTemplateManager {

    /**
     * 编译模板
     *
     * @param templateName 模板文件路径名称
     * @param attributes   模板参数映射
     * @return 返回模板编译后的内容
     */
    String compileTemplate(String templateName, Map<String, Object> attributes);
}
