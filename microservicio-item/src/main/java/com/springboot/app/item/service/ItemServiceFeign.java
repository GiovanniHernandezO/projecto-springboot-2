package com.springboot.app.item.service;

import com.springboot.app.item.clientes.IProductoClienteRest;
import com.springboot.app.item.models.Item;
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
}

