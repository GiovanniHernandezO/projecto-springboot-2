package com.springboot.app.item.controllers;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private Environment env;

    @Autowired
    @Qualifier("serviceFeign")
    private IItemService itemService;

    @GetMapping("/listar")
    public ResponseEntity<List<Item>> getAll() {
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
        return ResponseEntity.ok(itemService.findById(id, cantidad));
    }

    private void verPuerto() {
        System.out.println("puerto que está usando proyecto Producto: " + env.getProperty("local.server.port"));
    }
}
