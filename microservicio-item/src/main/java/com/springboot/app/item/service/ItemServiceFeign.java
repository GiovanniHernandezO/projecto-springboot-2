package com.springboot.app.item.service;

import com.springboot.app.item.clientes.IProductoClienteRest;
import com.springboot.app.item.models.Item;
import springboot.servicio.commons.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements IItemService {

    @Autowired
    private IProductoClienteRest productoClienteRest;

    @Override
    public List<Item> findAll() {
        System.out.println("Usando FEIGN");
        return productoClienteRest.getAll().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        System.out.println("Usando FEIGN");
        return new Item(productoClienteRest.getById(id), cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        return productoClienteRest.save(producto);
    }

    @Override
    public Producto update(Producto producto, Long id) {
        return productoClienteRest.update(producto, id);
    }

    @Override
    public void delete(Long id) {
        productoClienteRest.delete(id);
    }
}

