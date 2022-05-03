package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置@SpringBootTest就可以对IOC容器里的组件进行自动装配了
 * @author Su
 * @create 2022-05-02 15:08
 */
@SpringBootTest
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询表中所有数据
     */
    @Test
    public void testSelectList(){
        //通过条件构造器查询一个List集合,若没有条件,则可以设置null为参数
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

    }
    /**
     * 插入表中数据
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("张三");
        user.setAge(23);
        user.setEmail("180@qq.com");
        int result = userMapper.insert(user);
        System.out.println("影响行数:"+result);
        System.out.println("id"+user.getUid());
    }

    /**
     * 删除表中数据
     * 1、DELETE FROM user WHERE id=?
     * 2、DELETE FROM user WHERE name = ? AND age = ?
     * 3、DELETE FROM user WHERE id IN(? , ? , ?)
     * 逻辑删除
     * 4、 UPDATE t_user SET is_deleted=1 WHERE uid IN ( ? , ? , ? ) AND is_deleted=0
     */
    @Test
    public void testDelete(){
        //通过id删除用户信息
        int result = userMapper.deleteById(1521038593416544258L);
        System.out.println("影响行数:"+result);

        //通过name和age条件删除
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",23);
        int i = userMapper.deleteByMap(map);

        //进行多个id实现批量删除
        List<Long> list = Arrays.asList(1L, 2L, 3L);

        int i1 = userMapper.deleteBatchIds(list);

    }

    /**
     * 根据Id修改用户信息
     * UPDATE user SET name=?, email=? WHERE id=?
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setUid(1L);
        user.setName("JJ");
        user.setEmail("123@qq.com");

        int result = userMapper.updateById(user);
        System.out.println("影响行数:"+result);
    }

    /**
     * 查询表数据
     * 1、SELECT id,name,age,email FROM user WHERE id=?
     * 2、SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
     * 3、SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
     * 4、select id,name,age,email from user where id =?;
     */
    @Test
    public void testSelect(){
        //通过id查询用户信息
        User user = userMapper.selectById(1L);
        System.out.println(user);

        //通过id查询多条用户信息
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(System.out::println);

        //根据Map集合查询条件
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","Jack");
        map.put("age",20);
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);

        //查询表中所有数据 通过条件构造器查询一个List集合,若没有条件,则可以设置null为参数
        List<User> users2 = userMapper.selectList(null);
        users2.forEach(System.out::println);

        //自定义功能
        Map<String, Object> map1 = userMapper.selectMapById(1L);
        System.out.println(map1);

    }


}


























