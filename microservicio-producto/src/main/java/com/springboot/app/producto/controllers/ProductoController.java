package com.springboot.app.producto.controllers;

import springboot.servicio.commons.models.entity.Producto;
import com.springboot.app.producto.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        //return ResponseEntity.ok(productoService.findAll());
        return new ResponseEntity<List<Producto>>(productoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") Long id) throws InterruptedException {
        verPuerto();
        if (id.equals(10L)) {
            throw  new IllegalStateException("Producto no encontrado");
        }
        if (id.equals(3L)) {
            TimeUnit.SECONDS.sleep(5L);
        }

        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        //return ResponseEntity.ok(productoService.save(producto));
        return new ResponseEntity<Producto>(productoService.save(producto), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Producto> update(@RequestBody Producto producto, @PathVariable Long id) {
        Producto productoDB = productoService.findById(id);
        productoDB.setNombre(producto.getNombre());
        productoDB.setPrecio(producto.getPrecio());
        return new ResponseEntity<Producto>(productoService.save(productoDB), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productoService.deleteById(id);
        ResponseEntity.noContent();
    }
    * */

    private void verPuerto() {
        System.out.println("puerto que está usando proyecto Producto: " + env.getProperty("local.server.port"));
    }
}
