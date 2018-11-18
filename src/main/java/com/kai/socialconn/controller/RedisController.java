package com.kai.socialconn.controller;

import com.kai.socialconn.service.RedisRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/stringAndHash")
    @ResponseBody
    public Map<String, Object> testStringAndHash(){
        redisTemplate.opsForValue().set("key1", "value1");
        redisTemplate.opsForValue().set("int_key","1");
        stringRedisTemplate.opsForValue().set("int","1");
        stringRedisTemplate.opsForValue().increment("int",1);

        //get Jedis连接
        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        // 减1操作，这个命令RedisTemplate不支持，所以我先获取底层的连接再操作
        jedis.decr("int");
        Map<String, String> hash = new HashMap<>();
        hash.put("field1","value1");
        hash.put("field2","value2");
        //save hash
        stringRedisTemplate.opsForHash().putAll("hash",hash);
        stringRedisTemplate.opsForHash().put("hash", "field3", "value3");

        // 绑定散列操作的key，这样可以连续对同一个散列数据类型进行操作
        BoundHashOperations hashOperations = stringRedisTemplate.boundHashOps("hash");
        hashOperations.delete("field1","field2");
        hashOperations.put("field4","value4");

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> testList(){
        stringRedisTemplate.opsForList().leftPushAll("list1","v2","v4","v6","v8","v10");
        stringRedisTemplate.opsForList().rightPushAll("list2","v1","v2","v3","v4","v5","v6");

        BoundListOperations listOperations = stringRedisTemplate.boundListOps("list2");
        Object result1 = listOperations.rightPop();
        Object result2 = listOperations.index(1);

        listOperations.leftPush("v0");

        Long size = listOperations.size();
        List elements = listOperations.range(0, size-2);
        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        map.put("result1",result1);
        map.put("result2",result2);
        return  map;
    }

    @RequestMapping("/set")
    @ResponseBody
    public Map<String, Object> testSet(){
        //set value cannot be duplicated
        stringRedisTemplate.opsForSet().add("set1","v1","v2","v3","v4","v5");
        stringRedisTemplate.opsForSet().add("set2","v2","v4","v6","v8");

        BoundSetOperations setOperations = stringRedisTemplate.boundSetOps("set1");
        setOperations.add("v6","v7");
        setOperations.remove("v1","v7");

        Set set1 = setOperations.members();
        Long size = setOperations.size();

        //intersection
        Set inter =setOperations.intersect("set2");
        setOperations.intersectAndStore("set2","inter");

        //diff
        Set diff = setOperations.diff("set2");
        setOperations.diffAndStore("set2","diff");

        //union
        Set union = setOperations.union("set2");
        setOperations.unionAndStore("set2","union");

        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        map.put("inter",inter);
        map.put("diff",diff);
        map.put("union",union);
        return map;
    }

    @RequestMapping("/zset")
    @ResponseBody
    public Map<String, Object> testZset(){
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            double score = i*0.1;

            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value"+i,score);
            typedTupleSet.add(typedTuple);
        }

        stringRedisTemplate.opsForZSet().add("zset1",typedTupleSet);
        BoundZSetOperations<String, String> zSetOperations = stringRedisTemplate.boundZSetOps("zset1");
        zSetOperations.add("value10",0.26);
        Set<String> setRange = zSetOperations.range(1,6);

        Set<String> setScore = zSetOperations.rangeByScore(0.2,0.6);

        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gt("value3");//greater
        range.gte("value3");//greater and equal
        range.lt("value8");//less
        range.lte("value8");//less and equal
        Set<String> setLex = zSetOperations.rangeByLex(range);//order by value(letter)
        zSetOperations.remove("value9","value2");

        Double score = zSetOperations.score("value8");

        Set<ZSetOperations.TypedTuple<String>> rangeSet = zSetOperations.rangeWithScores(1,6);
        Set<ZSetOperations.TypedTuple<String>> scoreSet = zSetOperations.rangeByScoreWithScores(1,6);

        Set<String> reverseSet = zSetOperations.reverseRange(2,8);

        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("rangeSet",rangeSet);
        return map;
    }

    @RequestMapping("/hyperlog")
    @ResponseBody
    public Map<String, Object> testHyperLog(){
        stringRedisTemplate.opsForHyperLogLog().add("HyperLogLog", "a","b","c","d","a");
        stringRedisTemplate.opsForHyperLogLog().add("HyperLogLog2", "a");
        stringRedisTemplate.opsForHyperLogLog().add("HyperLogLog2", "z");
        stringRedisTemplate.opsForHyperLogLog().size("HyperLogLog");
        stringRedisTemplate.opsForHyperLogLog().union("des_hyperKey","HyperLogLog","HyperLogLog2");

        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    @RequestMapping("/multi")
    @ResponseBody
    public Map<String, Object> testMulti(){
        redisTemplate.opsForValue().set("multiTest_key1","value1");
        List list = (List)redisTemplate.execute((RedisOperations operations) -> {
            // 设置要监控key1
            operations.watch("key1");
            // 开启事务，在exec命令执行前，全部都只是进入队列
            operations.multi();
            operations.opsForValue().set("key2", "value2");
            // operations.opsForValue().increment("key1", 1);// ①
            // 获取值将为null，因为redis只是把命令放入队列
            Object value2 = operations.opsForValue().get("key2");
            System.out.println("命令在队列，所以value为null【"+ value2 +"】");
            operations.opsForValue().set("key3", "value3");
            Object value3 = operations.opsForValue().get("key3");
            System.out.println("命令在队列，所以value为null【"+ value3 +"】");
            // 执行exec命令，将先判别key1是否在监控后被修改过，如果是则不执行事务，否则就执行事务
            return operations.exec();// ②
        });
        System.out.println(list);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",true);
        return map;
    }

    @RequestMapping("/pipeline")
    @ResponseBody
    public Map<String, Object> testPipeline(){
        Long start = System.currentTimeMillis();
        List list = (List) redisTemplate.executePipelined((RedisOperations redisOps)->
        {
            for (int i = 0; i < 100000; i++) {
                redisOps.opsForValue().set("pipeline_" + i, "value_" + i);
                String value = (String) redisOps.opsForValue().get("pipeline_" + i);
                if (i == 100000) {
                    System.out.println("value will be null because haven't execute really.");
                }
            }
            return null;
        });
        Long end = System.currentTimeMillis();
        System.out.println("Pipeline 100000 spent:"+(end-start)+"millisecond.");
        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        map.put("spent",(end-start));
        return map;
    }

    @RequestMapping("/sendMsg")
    @ResponseBody
    public Map<String, Object> sendMsg(String msg){
        redisTemplate.convertAndSend("topic1",msg);

        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    @RequestMapping("/lua")
    @ResponseBody
    public Map<String,Object> testLua(){
        DefaultRedisScript<String> rs = new DefaultRedisScript<>();
        rs.setScriptText("return 'Hello Redis Lua'");
        rs.setResultType(String.class);//must define return type or will not get the result back.
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();

        String rst = (String) redisTemplate.execute(rs, stringRedisSerializer, stringRedisSerializer, null);


        String luaCompare = "redis.call('set',KEYS[1],ARGV[1]) \n"
                +"redis.call('set',KEYS[2],ARGV[2]) \n"
                +"local str1=redis.call('get',KEYS[1]) \n"
                +"local str2=redis.call('get',KEYS[2]) \n"
                +"if str1==str2 then \n"
                +"return 1 \n"
                +"end \n"
                +"return 0 \n";

        System.out.println(luaCompare);

        DefaultRedisScript<Long> rs2 = new DefaultRedisScript<>();
        rs2.setScriptText(luaCompare);
        rs2.setResultType(Long.class);

        List<String> keyList = new ArrayList<>();
        keyList.add("key1");
        keyList.add("key2");
        Long rst2 = (Long) redisTemplate.execute(rs2,stringRedisSerializer,stringRedisSerializer,keyList,"value1","value2");

        Map<String, Object> map = new HashMap<>();
        map.put("str",rst);
        return map;
    }

    @Autowired
    RedisRedPacketService rRps = null;

    @RequestMapping(value = "/grapRedPacketByRedis")
    @ResponseBody
    public Map<String, Object> grapRedPacketByRedis(Long redPacketId, Long userId){
        Map<String,Object> resultMap = new HashMap<>();
        Long result = rRps.grapRedPacketByRedis(redPacketId,userId);
        boolean flag = result>0;
        resultMap.put("result",flag);
        resultMap.put("message",flag?"grab red packet success.":"grab red packet failed.");
        return resultMap;
    }

    @RequestMapping(value = "/grapRedPacket")
    public String grapRedPacket(){
        return "redPacketPage";
    }
}
