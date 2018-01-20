package com.fmi110.springboot.test;

import com.fmi110.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * springboot redis 测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    /**
     * StringRedisTemplate 是 springboot 自动装配的 , 不需要显示声明bean
     */
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate           stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 1 测试存储字符串
     *
     * @throws InterruptedException
     */
    @Test
    public void testString() throws InterruptedException {
        // 5 秒自动过期
        stringRedisTemplate.opsForValue()
                           .set("key1", "value", 5, TimeUnit.SECONDS);
        String v1 = stringRedisTemplate.opsForValue()
                                       .get("key1");
        System.out.println("v1 = " + v1);  // 输出 "value"
        Thread.sleep(6000);
        System.out.println("v1 = " + stringRedisTemplate.opsForValue()
                                                        .get("key1"));  // 输出 null
    }

    /**
     * 2 测试存储 List
     */
    @Test
    public void testList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("java");
        list.add("c++");
        list.add("php");
        list.add("kotlin");
        list.add("java script");

        Long res = redisTemplate.opsForList()
                                .leftPushAll("list", list.toArray());

        List<Object> range = redisTemplate.opsForList()
                                          .range("list", 0, -1);

        System.out.println(range); // range = [java script, kotlin, php, c++, java]


        redisTemplate.opsForList()
                     .getOperations()
                     .delete("list");  // 删除指定 key 的数据

        range = redisTemplate.opsForList()
                             .range("list", 0, -1);
        System.out.println(range);  // range = []

        // 1 设置指定 key 数据的过期时间
        Boolean expire = redisTemplate.opsForList()
                                      .getOperations()
                                      .expire("list", 2, TimeUnit.SECONDS);
        // 2 判断指定 key 是否存在
        Boolean hasKey = redisTemplate.opsForList()
                                      .getOperations()
                                      .hasKey("list");
    }

    /**
     * 3 测试存储 Object 对象
     */
    @Test
    public void testObject() {
        User user = new User("fmi110", 18);

        redisTemplate.opsForValue()
                     .set("user", user);
        User u = (User) redisTemplate.opsForValue()
                                     .get("user");
        // 输出 : User{id=null, name='fmi110', age=18}
        System.out.println(u);


        User            user1 = new User("蜂蜜", 12);
        User            user2 = new User("itcast", 18);
        ArrayList<User> list  = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);
        redisTemplate.opsForList()
                     .leftPushAll("userList", list.toArray());
        // 输出 : [User{id=null, name='itcast', age=18}, User{id=null, name='蜂蜜', age=12}]
        System.out.println(redisTemplate.opsForList()
                                        .range("userList", 0, 1));
    }

    /**
     * 存储 hash 结构数据
     */
    @Test
    public void testHash() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "fmi110");
        map.put("class", "三年级一班");
        map.put("age", 18);

        redisTemplate.opsForHash()
                     .putAll("user1", map);

        Boolean hasKey = redisTemplate.opsForHash()
                                      .hasKey("user1", "name");
        // 删除 user1 中的 age
        Long delete = redisTemplate.opsForHash()
                                   .delete("user1", "age");
        // 获取 user1 中的 name 值
        Object name = redisTemplate.opsForHash()
                                   .get("user1", "name");

        // age值 +2 (不存在时添加)
        Long age = redisTemplate.opsForHash()
                                      .increment("user1", "age", 2);
        // 获取 user1 对应的散列表的所有key
        Set<Object> keys = redisTemplate.opsForHash()
                                          .keys("user1");
        // 获取 user1 对应的散列表对应的所有值
        List<Object> values = redisTemplate.opsForHash()
                                           .values("user1");
        // 获取 user1 中的所有数据,返回 map
        Map<Object, Object> user1 = redisTemplate.opsForHash()
                                                 .entries("user1");
        System.out.println(user1); // {name=fmi110, class=三年级一班}
        // 获取 user1 对应的游标,用于迭代获取数据 , redis 2.8.0 后可用
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
                                                                .scan("user1", ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            System.out.println(entry.getKey()+" ---> "+entry.getValue());
        }
    }

    /**
     * 存储 ZSet 数据,有序集合的成员是唯一的,但分数(score)却可以重复。
     */
    @Test
    public void testZSet(){

        Boolean zset1 = redisTemplate.opsForZSet()
                                     .add("zset", "zset-1", 1.0); // 添加成功返回true

        DefaultTypedTuple<Object> tuple2 = new DefaultTypedTuple<>("zset-2", 2.0);
        DefaultTypedTuple<Object> tuple3 = new DefaultTypedTuple<>("zset-3", 3.0);
        DefaultTypedTuple<Object> tuple4 = new DefaultTypedTuple<>("zset-4", 4.0);

        HashSet<ZSetOperations.TypedTuple<Object>> typedTuples = new HashSet<>();
        typedTuples.add(tuple2);
        typedTuples.add(tuple3);
        typedTuples.add(tuple4);

        Long size = redisTemplate.opsForZSet()
                                 .add("zset", typedTuples); // 添加或更新

        // 获取元素
        Set<Object> zset = redisTemplate.opsForZSet()
                                        .range("zset", 0, -1);

        // 输出 : [zset-1, zset-2, zset-3, zset-4]
        System.out.println(zset);
        // 移除 zset 中的 "zset-3"
        Long remove = redisTemplate.opsForZSet()
                                  .remove("zset", "zset-3");
        // 增加指定值的分数 + 2.0
        redisTemplate.opsForZSet()
                     .incrementScore("zset", "zset-2", 2.0d);
        // 返回指定值的排名 , 0 为排名第一 (分数有小到大排序)
        Long rank = redisTemplate.opsForZSet()
                                  .rank("zset", "zset-2");
        // 获取指定元素降序的排名
        Long reverseRank = redisTemplate.opsForZSet()
                                  .reverseRank("zset", "zset-1");

        // 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet()
                                                                    .rangeWithScores("zset", 0, -1);
        for (ZSetOperations.TypedTuple<Object> tuple : set) {
            System.out.println(tuple.getValue()+"  --->  "+tuple.getScore());
        }

//        Set<V> rangeByScore(K key, double min, double max);
//        通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
//
//        Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max);
//        通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
//
//        Set<V> rangeByScore(K key, double min, double max, long offset, long count);
//        通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
//
//        Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count);
//        通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
//
//        Set<V> reverseRange(K key, long start, long end);
//        通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
//
//        Long count(K key, double min, double max);
//        通过分数返回有序集合指定区间内的成员个数
//
//        Long size(K key);
//        获取有序集合的成员数，内部调用的就是zCard方法
//
//        Long zCard(K key);
//        获取有序集合的成员数
//
//        Double score(K key, Object o);
//        获取指定成员的score值
//
//        Long removeRange(K key, long start, long end);
//        移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
//
//        Long removeRangeByScore(K key, double min, double max);
//        根据指定的score值得范围来移除成员
//
//        Long unionAndStore(K key, K otherKey, K destKey);
//        计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
//
//        Long unionAndStore(K key, Collection<K> otherKeys, K destKey);
//        计算给定的多个有序集的并集，并存储在新的 destKey中
//
//        Long intersectAndStore(K key, K otherKey, K destKey);
//        计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
//
//        Long intersectAndStore(K key, Collection<K> otherKeys, K destKey);
//        计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
//
//        Cursor<TypedTuple<V>> scan(K key, ScanOptions options);
//        遍历zset

    }
}
