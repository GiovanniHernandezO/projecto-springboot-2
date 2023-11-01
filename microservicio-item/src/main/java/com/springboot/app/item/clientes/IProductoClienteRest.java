package com.springboot.app.item.clientes;

import com.springboot.app.item.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos", path = "/productos")
public interface IProductoClienteRest {

    @GetMapping("/listar")
    List<Producto> getAll();

    @GetMapping("/{id}")
    Producto getById(@PathVariable("id") Long id);

    @PostMapping("/crear")
    Producto save(@RequestBody Producto producto);

    @PutMapping("/actualizar/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable Long id);

    @DeleteMapping("/eliminar/{id}")
    void delete(@PathVariable Long id);
}
