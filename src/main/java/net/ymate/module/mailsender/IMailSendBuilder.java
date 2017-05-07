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

import java.io.File;
import java.util.Collection;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午2:00
 * @version 1.0
 */
public interface IMailSendBuilder {

    IMailSendBuilder to(String to);

    IMailSendBuilder to(String[] to);

    IMailSendBuilder to(Collection<String> to);

    //

    IMailSendBuilder cc(String cc);

    IMailSendBuilder cc(String[] cc);

    IMailSendBuilder cc(Collection<String> cc);

    //

    IMailSendBuilder bcc(String bcc);

    IMailSendBuilder bcc(String[] bcc);

    IMailSendBuilder bcc(Collection<String> bcc);

    //

    IMailSendBuilder subject(String subject);

    IMailSendBuilder level(IMailSender.Level level);

    IMailSendBuilder mimeType(IMailSender.MimeType mimeType);

    IMailSendBuilder charset(String charset);

    //

    IMailSendBuilder attachment(File file);

    IMailSendBuilder attachment(String cid, File file);

    //

    void send(String content) throws Exception;
}
