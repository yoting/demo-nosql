package com.gusi.demo.nosql.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * hello world
 */
public class RedisMain {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisBusiness redisBusinessSpring = (RedisBusiness) ac.getBean("redisBusinessBySpring");
		RedisBusiness redisBusinessJedis = (RedisBusiness) ac.getBean("redisBusinessByJedis");
		new RedisMain().doSpring(redisBusinessSpring);
		// new RedisMain().doJedis(redisBusinessJedis);
	}

	public void doSpring(RedisBusiness redisBusiness) {
		redisBusiness.demoString();
		redisBusiness.demoList();
		redisBusiness.demoSet();
		redisBusiness.demoHash();
		redisBusiness.demoZSet();
	}

	public void doJedis(RedisBusiness redisBusiness) {
		redisBusiness.demoString();
		redisBusiness.demoList();
		redisBusiness.demoSet();
		redisBusiness.demoHash();
		redisBusiness.demoZSet();
	}
}
