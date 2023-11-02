package com.springboot.app.item.service;
import springboot.servicio.commons.models.entity.Producto;

import com.springboot.app.item.models.Item;

import java.util.List;

public interface IItemService {
    List<Item> findAll();

    Item findById(Long id, Integer cantidad);

    Producto save(Producto producto);

    Producto update(Producto producto, Long id);

    void delete(Long id);
}
