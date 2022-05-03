package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Data相当于无参构造、getset方法、tostring方法、equals和Hash方法
 * @author Su
 * @create 2022-05-02 14:53
 */
@Data
//设置实体类对应的表名
//@TableName("t_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //属性对应的字段指定为主键
    //@Table注解的Value属性用于指定主键的字段
    //@Table注解的type属性用于指定主键的状态,自增没有用雪花算法
    @TableId(value = "uid",type = IdType.AUTO)
    private Long uid;

    //指定属性所对应的字段名
    @TableField("user_name")
    private String name;

    private Integer age;

    private String email;

    private SexEnum sex;

    //逻辑删除
    @TableLogic
    private Integer isDeleted;

}












