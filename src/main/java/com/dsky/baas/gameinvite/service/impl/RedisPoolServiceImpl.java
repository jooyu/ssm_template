package com.dsky.baas.gameinvite.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dsky.baas.gameinvite.service.IRedisPoolService;

@Service("redisPoolService")
public class RedisPoolServiceImpl implements ApplicationContextAware,IRedisPoolService{
	private static ApplicationContext ac;

	public ApplicationContext getApplicationContext() {
		return ac;
	}
	@Override
	public void setApplicationContext(ApplicationContext ac) {
		RedisPoolServiceImpl.ac = ac;
	}
	
	
	
	@Override
	public Jedis getConnect(){
		
    	JedisPool pool = (JedisPool)ac.getBean("jedisPool");
    	return pool.getResource();
//    	System.out.println(pool);
//    	System.out.println(pool);
//    	Jedis jedis =  pool.getResource();
//    	
//    	System.out.println("redis________________________________________con3");
//    	jedis.setex("test", 5000, "123222");
//    	System.out.println("redis________________________________________con4");
//    	String redisContent = jedis.get("test");
//    	System.out.println("redis________________________________________con5");
//    	System.out.println("redis____con "+redisContent);
//    	jedis.close();
	}

}
