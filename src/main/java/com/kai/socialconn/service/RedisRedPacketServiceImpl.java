package com.kai.socialconn.service;

import com.kai.socialconn.pojo.UserRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisRedPacketServiceImpl implements RedisRedPacketService {
    private static final String PREFIX = "red_packet_list_";

    private static final int TIME_SIZE = 1000;

    @Autowired
    private RedisTemplate redisTemplate = null;

    //@Autowired
    //private DataSource dataSource = null;

    /**
     * Asynchronized save redis data to DB
     *
     * @param redPacketId
     * @param unitAmount
     */
    @Override
    @Async
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount) {
        System.err.println("begin save data thread.");
        Long start = System.currentTimeMillis();
        BoundListOperations ops = redisTemplate.boundListOps(PREFIX + redPacketId);
        Long size = ops.size();
        Long times = size % TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;
        int count = 0;

        List<UserRedPacket> userRedPacketList = new ArrayList<>(TIME_SIZE);
        for (int i = 0; i < times; i++) {
            List userIdList = null;
            if (i == 0) {
                userIdList = ops.range(i * TIME_SIZE, (i + 1) * TIME_SIZE);
            } else {
                userIdList = ops.range(i * TIME_SIZE + 1, (i + 1) * TIME_SIZE);
            }
            userRedPacketList.clear();

            for (int j = 0; j < userIdList.size(); j++) {
                String args = userIdList.get(j).toString();
                String[] arr = args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];
                Long userId = Long.parseLong(userIdStr);
                Long time = Long.parseLong(timeStr);

                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setAmount(unitAmount);
                userRedPacket.setGrabTime(new Timestamp(time));
                userRedPacket.setNote("grab red packet: " + redPacketId);
                userRedPacketList.add(userRedPacket);
            }
            //count+=executeBatch(userRedPacketList);
        }

        redisTemplate.delete(PREFIX + redPacketId);
        Long end = System.currentTimeMillis();
        System.err.println("save data ended, spent time:" + (end - start) + "seconds, total" + count
                + "records been saved.");
    }

    //Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
            + "local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
            + "if stock <= 0 then return 0 end \n" + "stock = stock -1 \n"
            + "redis.call('hset', redPacket, 'stock', tostring(stock)) \n"
            + "redis.call('rpush', listKey, ARGV[1]) \n" + "if stock == 0 then return 2 end \n" + "return 1 \n";

    String sha1 = null;
    @Override
    public long grapRedPacketByRedis(Long redPacketId, Long userId) {
        String args = userId+"-"+System.currentTimeMillis();
        Long result = null;

        Jedis jedis =(Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try{
            if (sha1 == null) {
                sha1 = jedis.scriptLoad(script);
            }

            Object res = jedis.evalsha(sha1, 1, redPacketId+"",args);
            result = (Long) res;

            if(result==2){
                String unitAmountStr = jedis.hget("red_packet_"+redPacketId,"unit_amount");
                Double unitAmount = Double.parseDouble(unitAmountStr);
                System.err.println("thread_name = "+ Thread.currentThread().getName());
                //this.saveUserRedPacketByRedis(redPacketId,unitAmount);
            }
        }finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }

        return result;
    }
}
