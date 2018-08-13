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

import net.ymate.platform.core.lang.PairObject;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 16/12/17 上午2:27
 * @version 1.0
 */
public abstract class AbstractMailSendBuilder implements IMailSendBuilder {

    private List<String> to = new ArrayList<String>();

    private List<String> cc = new ArrayList<String>();

    private List<String> bcc = new ArrayList<String>();

    private String subject;

    private IMailSender.Level level;

    private IMailSender.MimeType mimeType;

    private String charset;

    private List<PairObject<String, File>> attachments = new ArrayList<PairObject<String, File>>();

    @Override
    public IMailSendBuilder to(String to) {
        String[] _tos = StringUtils.split(to, ";");
        if (_tos != null) {
            this.to(Arrays.asList(_tos));
        }
        return this;
    }

    @Override
    public IMailSendBuilder to(String[] to) {
        this.to.addAll(Arrays.asList(to));
        return this;
    }

    @Override
    public IMailSendBuilder to(Collection<String> to) {
        this.to.addAll(to);
        return this;
    }

    //

    @Override
    public IMailSendBuilder cc(String cc) {
        String[] _ccs = StringUtils.split(cc, ";");
        if (_ccs != null) {
            this.cc(Arrays.asList(_ccs));
        }
        return this;
    }

    @Override
    public IMailSendBuilder cc(String[] cc) {
        this.cc.addAll(Arrays.asList(cc));
        return this;
    }

    @Override
    public IMailSendBuilder cc(Collection<String> cc) {
        this.cc.addAll(cc);
        return this;
    }

    //

    @Override
    public IMailSendBuilder bcc(String bcc) {
        String[] _bccs = StringUtils.split(bcc, ";");
        if (_bccs != null) {
            this.bcc(Arrays.asList(_bccs));
        }
        return this;
    }

    @Override
    public IMailSendBuilder bcc(String[] bcc) {
        this.bcc.addAll(Arrays.asList(bcc));
        return this;
    }

    @Override
    public IMailSendBuilder bcc(Collection<String> bcc) {
        this.bcc.addAll(bcc);
        return this;
    }

    //

    @Override
    public IMailSendBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public IMailSendBuilder level(IMailSender.Level level) {
        this.level = level;
        return this;
    }

    @Override
    public IMailSendBuilder mimeType(IMailSender.MimeType mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    @Override
    public IMailSendBuilder charset(String charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public IMailSendBuilder attachment(File file) {
        this.attachments.add(new PairObject<String, File>(null, file));
        return this;
    }

    @Override
    public IMailSendBuilder attachment(String cid, File file) {
        this.attachments.add(new PairObject<String, File>(cid, file));
        return this;
    }

    /////

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public IMailSender.Level getLevel() {
        return level;
    }

    public IMailSender.MimeType getMimeType() {
        return mimeType;
    }

    public String getCharset() {
        return charset;
    }

    public List<PairObject<String, File>> getAttachments() {
        return attachments;
    }
}
