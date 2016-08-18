package com.dsky.baas.gameinvite.service;

import redis.clients.jedis.Jedis;

public interface IRedisPoolService {
	public Jedis getConnect();
}
