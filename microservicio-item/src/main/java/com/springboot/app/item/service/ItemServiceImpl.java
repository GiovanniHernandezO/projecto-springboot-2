package com.springboot.app.item.service;

import com.springboot.app.item.models.Item;
import springboot.servicio.commons.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService {

    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {
        System.out.println("Usando REST TEMPLATE - FIND ALL");
        List<Producto> productos = Arrays.asList(
                clienteRest.getForObject(
                        "http://servicio-productos/productos/listar",
                        Producto[].class
                )
        );
        return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        System.out.println("Usando REST TEMPLATE - FIND BY ID");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Producto producto = clienteRest.getForObject(
                "http://servicio-productos/productos/{id}",
                Producto.class,
                pathVariables
        );
        return new Item(producto, cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        System.out.println("Usando REST TEMPLATE - CREATE");
        HttpEntity<Producto> body = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = clienteRest
                .exchange(
                        "http://servicio-productos/productos/crear",
                        HttpMethod.POST,
                        body,
                        Producto.class
                );
        return response.getBody();
    }

    @Override
    public Producto update(Producto producto, Long id) {
        System.out.println("Usando REST TEMPLATE - UPDATE");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        HttpEntity<Producto> body = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = clienteRest
                .exchange(
                        "http://servicio-productos/productos/editar/{id}",
                        HttpMethod.PUT,
                        body,
                        Producto.class,
                        pathVariables
                );
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        System.out.println("Usando REST TEMPLATE - DELETE");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        clienteRest.delete("http://servicio-productos/productos/eliminar/{id}", pathVariables);
    }
}
