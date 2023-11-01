package com.springboot.app.producto.models.service;

import com.springboot.app.producto.models.dao.ProductoDao;
import com.springboot.app.producto.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productoDao.deleteById(id);
    }
}
