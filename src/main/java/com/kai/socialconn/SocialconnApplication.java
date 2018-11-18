package com.kai.socialconn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

@SpringBootApplication
//定义MyBatis的扫描
@MapperScan(basePackages = "com.kai.socialconn.*",annotationClass = Repository.class)
@EnableCaching
public class SocialconnApplication {
	//平台事务管理器，Mybatis一般为DataSourceTransactionManager
	@Autowired
	PlatformTransactionManager platformTransactionManager = null;

	@Autowired
	private RedisTemplate redisTemplate = null;

	//Redis connection factory
	@Autowired
	private RedisConnectionFactory connectionFactory =null;

	//Redis message listener
	@Autowired
	private MessageListener redisMsgListener = null;

	//task pools
	private ThreadPoolTaskScheduler taskScheduler = null;

	/**
	 *create task pools
	 * @return
	 */
	@Bean
	public ThreadPoolTaskScheduler initTaskSchedualer(){
		if (taskScheduler != null) {
			return taskScheduler;
		}

		taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(20);
		return taskScheduler;
	}

	/**
	 * create redis listener monitor repository/container
	 */
	@Bean
	public RedisMessageListenerContainer initRedisContainer(){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setTaskExecutor(taskScheduler);

		Topic topic = new ChannelTopic("topic1");

		container.addMessageListener(redisMsgListener,topic);
		return container;
	}

	@PostConstruct
	public void viewTransactionManger(){
		System.out.println("测试实物管理器"+platformTransactionManager.getClass().getSimpleName());
		//initialize redis template
		initRedisTemplate();
	}

	private void initRedisTemplate(){
		RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
	}

	public static void main(String[] args) {
		SpringApplication.run(SocialconnApplication.class, args);
	}
}
