package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.Product;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/18
 * 描述： ${todo}.
 */
public interface ProductDao {

    Product queryById(int id);

    void add(Product product);

    void update(Product product);

    List<Product> queryAll();
}
