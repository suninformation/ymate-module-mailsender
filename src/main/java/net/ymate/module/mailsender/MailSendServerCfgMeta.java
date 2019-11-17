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

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/7 下午9:48
 * @version 1.0
 */
public class MailSendServerCfgMeta {

    private Session __mailSession;

    private String name;

    private String displayName;

    private String fromAddr;

    private String smtpHost;

    private int smtpPort;

    private String smtpUsername;

    private String smtpPassword;

    private boolean debugEnabled;

    private boolean needAuth;

    private boolean tlsEnabled;

    private boolean sslEnabled;

    private boolean socketFactoryFallback;

    private String socketFactoryClassName;

    public MailSendServerCfgMeta(String name, String smtpHost, boolean tlsEnabled, boolean sslEnabled) {
        if (StringUtils.isBlank(name)) {
            throw new NullArgumentException("name");
        }
        if (StringUtils.isBlank(smtpHost)) {
            throw new NullArgumentException("smtpHost");
        }
        this.name = name;
        this.smtpHost = smtpHost;
        this.tlsEnabled = tlsEnabled;
        this.sslEnabled = sslEnabled;
    }

    public MailSendServerCfgMeta(String name, String smtpHost, boolean tlsEnabled, boolean sslEnabled, String smtpUsername, String smtpPassword) {
        this(name, smtpHost, tlsEnabled, sslEnabled);
        if (StringUtils.isBlank(smtpUsername)) {
            throw new NullArgumentException("smtpUsername");
        }
        if (StringUtils.isBlank(smtpPassword)) {
            throw new NullArgumentException("smtpPassword");
        }
        this.needAuth = true;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
    }

    public Session createIfNeed() {
        if (__mailSession == null) {
            synchronized (this) {
                if (__mailSession == null) {
                    Properties _props = new Properties();
                    _props.put("mail.smtp.host", smtpHost);
                    _props.put("mail.smtp.auth", needAuth);
                    _props.put("mail.transport.protocol", "smtp");
                    if (smtpPort <= 0) {
                        if (sslEnabled) {
                            this.smtpPort = 465;
                        } else if (tlsEnabled) {
                            this.smtpPort = 587;
                        } else {
                            this.smtpPort = 25;
                        }
                    }
                    if (tlsEnabled) {
                        _props.put("mail.smtp.starttls.enable", true);
                    } else if (sslEnabled) {
                        _props.put("mail.smtp.socketFactory.port", smtpPort);
                        _props.put("mail.smtp.socketFactory.class", StringUtils.defaultIfBlank(socketFactoryClassName, "javax.net.ssl.SSLSocketFactory"));
                        _props.put("mail.smtp.socketFactory.fallback", socketFactoryFallback);
                    }
                    _props.put("mail.smtp.port", smtpPort);
                    //
                    __mailSession = Session.getInstance(_props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(smtpUsername, smtpPassword);
                        }
                    });
                    __mailSession.setDebug(debugEnabled);
                }
            }
        }
        return __mailSession;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public boolean isTlsEnabled() {
        return tlsEnabled;
    }

    public void setTlsEnabled(boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public boolean isSocketFactoryFallback() {
        return socketFactoryFallback;
    }

    public void setSocketFactoryFallback(boolean socketFactoryFallback) {
        this.socketFactoryFallback = socketFactoryFallback;
    }

    public String getSocketFactoryClassName() {
        return socketFactoryClassName;
    }

    public void setSocketFactoryClassName(String socketFactoryClassName) {
        this.socketFactoryClassName = socketFactoryClassName;
    }
}
