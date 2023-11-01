package com.springboot.app.item.controllers;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("serviceFeign")
    private IItemService itemService;

    @GetMapping("/listar")
    public ResponseEntity<List<Item>> getAll(
            @RequestParam(name = "nombre", required = false) String nombreParametro,
            @RequestHeader(name= "token-request", required = true, defaultValue = "estoes ") String tokenRequest) {
        LOGGER.info(String.format("nombre '%s'", nombreParametro));
        LOGGER.info(String.format("token-request '%S'", tokenRequest));
        verPuerto();
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/producto/{idProducto}/cantidad/{cantidad}")
    public ResponseEntity<Item> getById(@PathVariable("idProducto") Long id,
                                        @PathVariable("cantidad") Integer cantidad,
                                        @RequestHeader Map<String, String> headers) {
        verPuerto();
        headers.forEach((key, value) -> {
            LOGGER.info(String.format("Header '%s' = %s", key, value));
        });
        return circuitBreakerFactory.create("items")
                .run(() -> ResponseEntity.ok(itemService.findById(id, cantidad)),
                        e -> fallbackGetById(id, cantidad, e));
    }

    private void verPuerto() {
        System.out.println("puerto que está usando proyecto Producto: " + env.getProperty("local.server.port"));
    }

    public ResponseEntity<Item> fallbackGetById(Long id, Integer cantidad, Throwable e) {
        LOGGER.error("Error controlado - " + e.getMessage());
        LOGGER.error("Error controlado - " + e.getLocalizedMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("iPhone 15 Pro Max");
        producto.setPrecio(1500.00);
        item.setProducto(producto);
        return ResponseEntity.ok(item);
    }
}
