package com.kai.socialconn.redisHelper;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String body = new String(message.getBody());
        String topic = new String(pattern);

        System.out.println("redis message body:"+body);
        System.out.println("redis message topic:"+topic);
    }
}
