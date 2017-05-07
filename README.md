### MailSender

基于YMP框架实现的邮件发送服务模块，支持多帐号配置；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.module</groupId>
        <artifactId>ymate-module-mailsender</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

### 模块配置参数说明

    #-------------------------------------
    # module.mailsender 模块初始化参数
    #-------------------------------------
    
    # 邮件发送服务提供者类, 默认值: net.ymate.module.mailsender.impl.DefaultMailSendProvider
    ymp.configs.module.mailsender.provider_class=
    
    # 发送线程池初始化大小, 默认值: Runtime.getRuntime().availableProcessors()
    ymp.configs.module.mailsender.thread_pool_size=
    
    # 默认自定义显示名称, 默认值: ""
    ymp.configs.module.mailsender.default_display_name=
    
    # 默认发送者邮件地址, 默认值: ""
    ymp.configs.module.mailsender.default_from_addr=
    
    # 默认邮件发送服务名称, 默认值: default
    ymp.configs.module.mailsender.default_server_name=
    
    # 是否开启调试模式, 默认值: false
    ymp.configs.module.mailsender.debug_enabled=
    
    # 邮件发送服务列表, 多个服务名称间用'|'分隔，默认值: default
    ymp.configs.module.mailsender.server_name_list=
    
    # SMTP服务器地址
    ymp.configs.module.mailsender.server.default.smtp_host=
    
    # SMTP端口, 默认值: 25, 当TLS开启时默认为465
    ymp.configs.module.mailsender.server.default.smtp_port=
    
    # 自定义显示名称, 默认值: ""
    ymp.configs.module.mailsender.server.default.display_name=
    
    # 发送者邮件地址, 默认值: ""
    ymp.configs.module.mailsender.server.default.from_addr=
    
    # 是否需要身份验证, 默认值: true
    ymp.configs.module.mailsender.server.default.need_auth=
    
    # SMTP服务登录用户名称
    ymp.configs.module.mailsender.server.default.smtp_username=
    
    # SMTP服务登录密码
    ymp.configs.module.mailsender.server.default.smtp_password=
    
    # 密码类参数是否已加密, 默认值: false
    ymp.configs.module.mailsender.server.default.password_encrypted=
    
    # 密码处理器, 可选参数, 用于对已加密密码进行解密, 默认值: 空
    ymp.configs.module.mailsender.server.default.password_class=
    
    # 是否开启TLS, 默认值: false
    ymp.configs.module.mailsender.server.default.tls_enabled=
    
    # 开启TLS时有效, 默认值: javax.net.ssl.SSLSocketFactory
    ymp.configs.module.mailsender.server.default.socket_factory_class=
    
    # 开启TLS时有效, 默认值: false
    ymp.configs.module.mailsender.server.default.socket_factory_fallback=

#### One More Thing

YMP不仅提供便捷的Web及其它Java项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入 官方QQ群480374360，一起交流学习，帮助YMP成长！

了解更多有关YMP框架的内容，请访问官网：http://www.ymate.net/