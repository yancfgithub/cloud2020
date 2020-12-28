package com.atguigu.springcloud.service.impl;

import cn.hutool.core.lang.UUID;
import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;


@EnableBinding(Source.class)
public class IMessageProviderImpl implements IMessageProvider {

    //这里不再和dao进行交流，而是和消息中间件进行消息的交互
    @Autowired
    private MessageChannel output;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("*****serial"+serial);
        return null;
    }
}
