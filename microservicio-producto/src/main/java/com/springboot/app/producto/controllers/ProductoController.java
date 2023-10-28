package com.springboot.app.producto.controllers;

import com.springboot.app.producto.models.entity.Producto;
import com.springboot.app.producto.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private Environment env;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> getAll() {
        verPuerto();
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") Long id) {
        verPuerto();
        return ResponseEntity.ok(productoService.findById(id));
    }

    private void verPuerto() {
        System.out.println("puerto que está usando proyecto Producto: " + env.getProperty("local.server.port"));
    }
}
