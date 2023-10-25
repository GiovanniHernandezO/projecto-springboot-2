package com.springboot.app.producto.models.dao;

import com.springboot.app.producto.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoDao extends JpaRepository<Producto, Long> {
}
