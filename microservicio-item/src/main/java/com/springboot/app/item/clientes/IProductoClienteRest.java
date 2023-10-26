package com.springboot.app.item.clientes;

import com.springboot.app.item.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos", url = "http://localhost:8001", path = "/productos")
public interface IProductoClienteRest {

    @GetMapping("/listar")
    List<Producto> getAll();

    @GetMapping("/{id}")
    Producto getById(@PathVariable("id") Long id);
}
