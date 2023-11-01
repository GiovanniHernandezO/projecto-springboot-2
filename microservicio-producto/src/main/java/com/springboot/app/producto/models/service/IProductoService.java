package com.springboot.app.producto.models.service;

import com.springboot.app.producto.models.entity.Producto;
import java.util.List;

public interface IProductoService {
    List<Producto> findAll();

    Producto findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);
}
