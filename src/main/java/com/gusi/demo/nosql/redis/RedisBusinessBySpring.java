package com.gusi.demo.nosql.redis;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ScanOptions.ScanOptionsBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisBusinessBySpring implements RedisBusiness {
	@Autowired
	private StringRedisTemplate redisTemplate;

	public void demoString() {
		System.out.println("redis:string");
		// 将key和数据绑定在bvo对象上，之后的操作都在这个绑定对象上操作即可
		BoundValueOperations<String, String> bvo = redisTemplate.boundValueOps("demoString");
		bvo.set("1");// String类型的数据各种操作
		bvo.increment(20);
		Object v = bvo.get();
		System.out.println(v);// 21
		bvo.expire(60, TimeUnit.SECONDS);// 设置过期时间
	}

	public void demoList() {
		System.out.println("redsi:list");
		// 将key和数据绑定在blo对象上
		BoundListOperations<String, String> blo = redisTemplate.boundListOps("demoList");
		blo.leftPush("a");
		blo.leftPush("b");
		blo.rightPush("c");
		blo.rightPush("d");
		blo.leftPop();
		blo.rightPop(10, TimeUnit.SECONDS);// 阻塞式的获取队列中的对象
		List list = blo.range(0, 10);// 获取队列前10个对象
		System.out.println(list);// a,c
	}

	public void demoSet() {
		System.out.println("redis:set");
		// 将key和数据绑定在bso对象上
		BoundSetOperations<String, String> bso = redisTemplate.boundSetOps("demoSet");
		bso.add("aa", "ab", "ac", "ac");
		bso.isMember("aa");
		bso.randomMember();
		bso.remove("aa");
		Cursor<String> cursor = bso.scan(ScanOptions.NONE);// 通过游标扫描满足条件的元素
		while (cursor.hasNext()) {
			System.out.println(cursor.next());// ac,ab
		}
		cursor = bso.scan(new ScanOptionsBuilder().match("a*").build());
		while (cursor.hasNext()) {
			System.out.println(cursor.next());// ac,ab
		}
	}

	public void demoHash() {
		System.out.println("redis:hash");
		// 将key和数据绑定在bho对象上
		BoundHashOperations<String, String, String> bho = redisTemplate.boundHashOps("demoHash");
		bho.put("a", "1");
		Map<String, String> map = bho.entries();// hash的操作其实操作的相当于map
		long size = bho.size();
		System.out.println(size);// 1

		bho.expireAt(new Date(System.currentTimeMillis() + 100000L));// 通过设置过期节点来设置数据失效时间
	}

	public void demoZSet() {
		System.out.println("redis:zset");
		// 将key和数据绑定在hzso对象上
		BoundZSetOperations<String, String> bzso = redisTemplate.boundZSetOps("demoZSet");
		bzso.add("a", 10);
		bzso.add("b", 11);
		bzso.add("c", 9.9);
		bzso.add("d", 11.1);
		bzso.count(10, 11);
		long zcard = bzso.zCard();
		Set set = bzso.rangeByScore(0, 12);// 通过分数查询集合元素
		System.out.println(zcard);// 4
		System.out.println(set);// c,a,b,d
	}
}
