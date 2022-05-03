package com.atguigu.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author Su
 * @create 2022-05-03 15:35
 */
@Getter
public enum SexEnum {
    MALE(1,"男"),
    FEMALE(2,"女");

    @EnumValue //将注解所标识的属性的值存储到数据库中
    private Integer sex;

    private String setName;

    SexEnum(Integer sex, String setName) {
        this.sex = sex;
        this.setName = setName;
    }
}
