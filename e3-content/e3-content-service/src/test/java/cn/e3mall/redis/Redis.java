package cn.e3mall.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {

	@Test
	public void testRedis() throws Exception{
		Jedis jedis=new Jedis("95.169.31.254",6379);
		jedis.set("aa", "1");
		String string = jedis.get("aa");
		System.out.println(string);
	}
	
	//使用单机版本连接连接池
	@Test
	public void testJedisPool() throws Exception
	{
		JedisPool jedisPool =new JedisPool("95.169.31.254",6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("jedis", "test");
		String result=jedis.get("jedis");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}
}
