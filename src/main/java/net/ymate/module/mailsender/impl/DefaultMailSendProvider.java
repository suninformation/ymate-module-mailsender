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

import net.ymate.module.mailsender.*;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午2:41
 * @version 1.0
 */
public class DefaultMailSendProvider implements IMailSendProvider {

    private ExecutorService __sendExecPool;

    private IMailSender __owner;

    @Override
    public void init(IMailSender owner) throws Exception {
        __owner = owner;
        __sendExecPool = Executors.newFixedThreadPool(owner.getModuleCfg().getThreadPoolSize());
    }

    @Override
    public void destroy() throws Exception {
        if (__sendExecPool != null) {
            __sendExecPool.shutdown();
            __sendExecPool = null;
        }
        __owner = null;
    }

    @Override
    public IMailSendBuilder create() {
        return create(__owner.getModuleCfg().getDefaultMailSendServerCfg());
    }

    @Override
    public IMailSendBuilder create(final MailSendServerCfgMeta serverCfgMeta) {
        return new AbstractMailSendBuilder() {
            @Override
            public void send(final String content) throws Exception {
                __sendExecPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MimeMessage _message = new MimeMessage(serverCfgMeta.createIfNeed());
                            //
                            for (String _to : getTo()) {
                                _message.addRecipient(Message.RecipientType.TO, new InternetAddress(_to));
                            }
                            for (String _cc : getCc()) {
                                _message.addRecipient(Message.RecipientType.CC, new InternetAddress(_cc));
                            }
                            for (String _bcc : getBcc()) {
                                _message.addRecipient(Message.RecipientType.BCC, new InternetAddress(_bcc));
                            }
                            //
                            if (getLevel() != null) {
                                switch (getLevel()) {
                                    case LEVEL_HIGH:
                                        _message.setHeader("X-MSMail-Priority", "High");
                                        _message.setHeader("X-Priority", "1");
                                        break;
                                    case LEVEL_NORMAL:
                                        _message.setHeader("X-MSMail-Priority", "Normal");
                                        _message.setHeader("X-Priority", "3");
                                        break;
                                    case LEVEL_LOW:
                                        _message.setHeader("X-MSMail-Priority", "Low");
                                        _message.setHeader("X-Priority", "5");
                                        break;
                                    default:
                                }
                            }
                            //
                            String _charset = StringUtils.defaultIfEmpty(getCharset(), "UTF-8");
                            _message.setFrom(new InternetAddress(serverCfgMeta.getFromAddr(), serverCfgMeta.getDisplayName(), _charset));
                            _message.setSubject(getSubject(), _charset);
                            // 整体
                            Multipart _container = new MimeMultipart();
                            // 正文部分
                            MimeBodyPart _textBodyPart = new MimeBodyPart();
                            if (getMimeType() == null) {
                                mimeType(IMailSender.MimeType.TEXT_PLAIN);
                            }
                            _textBodyPart.setContent(content, getMimeType().getMimeType() + ";charset=" + _charset);
                            _container.addBodyPart(_textBodyPart);
                            // 附件部分，资源引用方式：<img src="cid:<CID_NAME>">
                            for (PairObject<String, File> _file : getAttachments()) {
                                if (_file.getValue() != null) {
                                    MimeBodyPart _fileBodyPart = new MimeBodyPart();
                                    FileDataSource _fileDS = new FileDataSource(_file.getValue());
                                    _fileBodyPart.setDataHandler(new DataHandler(_fileDS));
                                    if (_file.getKey() != null) {
                                        _fileBodyPart.setHeader("Content-ID", _file.getKey());
                                    }
                                    _fileBodyPart.setFileName(_fileDS.getName());
                                    _container.addBodyPart(_fileBodyPart);
                                }
                            }
                            // 执行发送
                            _message.setContent(_container);
                            Transport.send(_message);
                        } catch (Exception e) {
                            throw new RuntimeException(RuntimeUtils.unwrapThrow(e));
                        }
                    }
                });
            }
        };
    }
}
