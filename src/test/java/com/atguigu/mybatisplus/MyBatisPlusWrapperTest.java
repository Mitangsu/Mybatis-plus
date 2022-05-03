package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author Su
 * @create 2022-05-03 3:07
 */
@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
     *
     * is_deleted没有查出来因为是逻辑删除
     */
    @Test
    public void test1(){
        //查询用户名包含a,年龄在20到30之间,并且邮箱信息不为Null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name","a")
                .between("age",20,30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0
     * ORDER BY age DESC,uid ASC
     */
    @Test
    public void test2(){
        //查询用户信息,按照年龄的降序排序,若年龄相同,则按照id升序排序
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                .orderByAsc("uid");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    /**
     * UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
     * 因为有逻辑删除所以删除是UPDATE
     */
    @Test
    public void test3(){
        //删除邮件地址为null的用户信息
       QueryWrapper<User> queryWrapper = new QueryWrapper<>();
       queryWrapper.isNull("email");
        int delete = userMapper.delete(queryWrapper);
        System.out.println("影响"+delete);

    }

    /**
     * UPDATE t_user SET user_name=?
     * WHERE is_deleted=0
     * AND (age > ? AND user_name LIKE ? OR email IS NULL)
     */
    @Test
    public void test4(){
        //将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改W
        //gt是大于的意思
        //连接默认是and
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",20)
                .like("user_name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("小三");
        user.setEmail("222@qq.com");
        int update = userMapper.update(user, queryWrapper);
        System.out.println(update);
    }

    /**
     * UPDATE t_user SET user_name=?, email=?
     * WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    public void test05(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        //Lambda中的条件优先执行
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("user_name","a")
                .and(i->i.gt("age",20).or().isNull("email"));
        User user = new User();
        user.setName("小四");
        user.setEmail("222@qq.com");
        int update = userMapper.update(user, queryWrapper);
        System.out.println(update);

    }

    /**
     *  SELECT user_name,age,email FROM t_user WHERE is_deleted=0
     */
    @Test
    public void test06(){
        //查询用户的用户名、年龄、邮箱信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name","age","email");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid <= 100))
     */
    @Test
    public void test07(){
        //查询id小于等于100的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("uid","select uid from t_user where uid <= 100");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * UPDATE t_user SET user_name=?,email=?
     * WHERE is_deleted=0
     * AND (user_name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    public void test08(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        UpdateWrapper<User> updateWrapper =new UpdateWrapper<>();
        updateWrapper.like("user_name","a")
                .and(i ->i.gt("age",20).or().isNull("email"));
        updateWrapper.set("user_name","小黑").set("email","aaa@qq.com");
        int update = userMapper.update(null, updateWrapper);
        System.out.println(update);

    }

    /**
     *  SELECT uid,user_name AS name,age,email,is_deleted
     *  FROM t_user
     *  WHERE is_deleted=0 AND (age >= ? AND age <= ?)
     */
    @Test
    public void test09(){
        String username="";
        Integer ageBegin=20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            //isNotBlank判断某个字符串是否不为空字符串, 不为null,不为空白符
            queryWrapper.like("user_name",username);
        }
        if (ageBegin!=null){
            //ge大于等于
            queryWrapper.ge("age",ageBegin);
        }
        if (ageEnd!=null){
            //le小于等于
            queryWrapper.le("age",ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
     */
    @Test
    public void test10(){
        String username="a";
        Integer ageBegin=null;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"user_name",username)
                .ge(ageBegin!=null,"age",ageBegin)
                .le(ageEnd!=null,"age",ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0 AND (user_name LIKE ? AND age >= ? AND age <= ?)
     */
    @Test
    public void test11(){
        String username="a";
        Integer ageBegin=20;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,username)
                .ge(ageBegin!=null,User::getAge,ageBegin)
                .le(ageEnd!=null,User::getAge,ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    /**
     * UPDATE t_user SET user_name=?,email=?
     * WHERE is_deleted=0
     * AND (user_name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    public void test12(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> updateWrapper =new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName,"a")
                .and(i ->i.gt(User::getAge,20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName,"小黑").set(User::getEmail,"aaa@qq.com");
        int update = userMapper.update(null, updateWrapper);
        System.out.println(update);

    }



}
















