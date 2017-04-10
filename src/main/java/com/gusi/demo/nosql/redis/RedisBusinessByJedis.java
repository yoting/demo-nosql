package com.gusi.demo.nosql.redis;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;

@Service
public class RedisBusinessByJedis implements RedisBusiness {
	@Autowired
	private JedisPool jedisPool;// redis连接池，通过getResource方法可以获得Jedis对象，可用Jedis对象来操作数据

	public void demoString() {
		System.out.println("redis:string");
		jedisPool.getResource().set("demoString", "1");
		jedisPool.getResource().incrBy("demoString", 20);
		String value = jedisPool.getResource().get("demoString");
		System.out.println(value);// 21
		jedisPool.getResource().expire("demoString", 60);// 设置过期时间在60秒以后
	}

	public void demoList() {
		System.out.println("redis:list");
		jedisPool.getResource().lpush("demoList", "a", "b", "c", "d");
		jedisPool.getResource().rpush("demoList", "c", "d");
		jedisPool.getResource().lpop("demoList");
		jedisPool.getResource().rpop("demoList");

		List list = jedisPool.getResource().lrange("demoList", 0, 10);// 获取列表中的前10个元素
		System.out.println(list);// c,b,a,c
	}

	public void demoSet() {
		System.out.println("redis:set");
		jedisPool.getResource().sadd("demoSet", "aa", "ab", "ac", "ac");// set中重复元素最终只会有一个
		Set set = jedisPool.getResource().smembers("demoSet");
		boolean ismember = jedisPool.getResource().sismember("demoSet", "aa");
		String pop = jedisPool.getResource().spop("demoSet");

		System.out.println(set);// aa,ac,ab 这个结果不一定
		System.out.println(ismember);// true
		System.out.println(pop);// ac 这个结果不一定
	}

	public void demoHash() {
		System.out.println("redis:hash");
		jedisPool.getResource().hset("demoHash", "a", "1");// hash就相当于往key对应的数据中存放一个map
		jedisPool.getResource().hset("demoHash", "b", "2");
		String a = jedisPool.getResource().hget("demoHash", "a");
		List ab = jedisPool.getResource().hmget("demoHash", "a", "b");
		System.out.println(a);// 1
		System.out.println(ab);// 1,2

		jedisPool.getResource().expireAt("demoHash", System.currentTimeMillis() / 1000L + 60);// 通过设置过期时间点设置数据过期
	}

	public void demoZSet() {
		System.out.println("redis:zset");
		jedisPool.getResource().zadd("demoZSet", 10, "a");// zset中的每个元素都有一个分值
		jedisPool.getResource().zadd("demoZSet", 11, "b");
		jedisPool.getResource().zadd("demoZSet", 9.9, "c");
		jedisPool.getResource().zadd("demoZSet", 11.1, "d");
		jedisPool.getResource().zcount("demoZSet", 10, 11);
		long zcard = jedisPool.getResource().zcard("demoZSet");
		Set set = jedisPool.getResource().zrange("demoZSet", 0, 3);// 获取指定条件下的元素集合
		Set set1 = jedisPool.getResource().zrevrangeByScore("demoZSet", 12, 10);
		System.out.println(zcard);// 4
		System.out.println(set);// c,a,b,d
		System.out.println(set1);// d,b,a
	}
}
