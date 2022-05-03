package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Su
 * @create 2022-05-02 19:27
 */
@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 获取总记录数
     * SELECT COUNT( * ) FROM user
     */
    @Test
    public void testGetCount(){
        long count = userService.count();
        System.out.println("总记录数:"+count);
    }

    /**
     * 批量添加
     *  INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
     */
    @Test
    public void testInsertMore(){
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("张"+i);
            user.setAge(20+i);
            list.add(user);
        }

        boolean b = userService.saveBatch(list);
        System.out.println("操作是"+b);
    }

    /**
     * 批量查询操作
     * SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? , ? , ? )
     */
    @Test
    public void testQuery(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<User> users = userService.listByIds(list);
        users.forEach(System.out::println);
    }


    /**
     * 删除操作
     *  DELETE FROM user WHERE id=?
     */
    @Test
    public void testDelete(){
        List<Long> list = Arrays.asList(1521093025734864898L, 1521093025734864899L);
        boolean b = userService.removeBatchByIds(list);
        System.out.println("操作是"+b);
    }

    /**
     * 修改表操作
     * SELECT id,name,age,email FROM user WHERE id=?
     * UPDATE user SET name=?, email=? WHERE id=?
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setUid(1L);
        user.setName("GG");
        user.setEmail("123@qq.com");
        boolean b = userService.saveOrUpdate(user);
        System.out.println("操作是"+b);


    }
}










