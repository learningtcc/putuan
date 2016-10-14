package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.ProductDao;
import com.advanpro.putuan.model.Product;
import com.advanpro.putuan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/20
 * 描述： ${todo}.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> list() {
        return productDao.queryAll();
    }

    @Override
    public void add(Product product) {
        productDao.add(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public List<Product> queryAll() {
        return productDao.queryAll();
    }

    @Override
    public Product queryById(int id) {
        return productDao.queryById(id);
    }
}
