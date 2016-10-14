package com.advanpro.putuan.service;

import com.advanpro.putuan.model.Product;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public interface ProductService {

    List<Product> list();

    void add(Product product);

    void update(Product product);

    List<Product> queryAll();

    Product queryById(int id);
}