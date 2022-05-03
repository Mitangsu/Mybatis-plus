package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author Su
 * @create 2022-05-03 14:59
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version //用来标识乐观锁版本号字段
    private Integer version;
}
