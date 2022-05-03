package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Su
 * @create 2022-05-03 13:55
 */
@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired(required = false)
    private ProductMapper productMapper;

    /**
     * SELECT uid,user_name AS name,age,email,is_deleted
     * FROM t_user
     * WHERE is_deleted=0
     * LIMIT ?
     */
    @Test
    public void testPage(){
        Page<User> page = new Page<>(1,3);
        userMapper.selectPage(page, null);
        //获取当前页的数据
        System.out.println(page.getRecords());
        //获取当前页的页码
        System.out.println(page.getCurrent());
        //获取当前页的条数
        System.out.println(page.getSize());
        //获取总记录数
        System.out.println(page.getTotal());
        //获取是否有上一页
        System.out.println(page.hasPrevious());
        //获取是否有下一页
        System.out.println(page.hasNext());

    }

    /**
     *  select uid,user_name,age,email
     *  from t_user
     *  where age > ? LIMIT ?
     */
    @Test
    public void testPageVo(){
        Page<User> page = new Page<>(1,3);
        userMapper.selectPageVo(page,20);
    }

    /**
     * 1、SELECT id,name,price,version FROM t_product WHERE id=?
     * 2、UPDATE t_product SET name=?, price=?, version=? WHERE id=? AND version=?
     */
    @Test
    public void testProduct01(){
        //小李查询商品价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李查询的商品价格"+productLi.getPrice());
        //小王查询商品价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小王查询的商品价格"+productWang.getPrice());

        //小李将商品价格+50
        productLi.setPrice(productLi.getPrice()+50);
        productMapper.updateById(productLi);

        //小王将商品价格-30
        productWang.setPrice(productWang.getPrice()-30);
        int result = productMapper.updateById(productWang);
        if (result == 0){
            //操作失败,重试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice()-30);
            productMapper.updateById(productNew);
        }

        //老板查询商品价格
        Product productBoss = productMapper.selectById(1);
        System.out.println("老板查询的商品价格"+productBoss.getPrice());


    }
}








