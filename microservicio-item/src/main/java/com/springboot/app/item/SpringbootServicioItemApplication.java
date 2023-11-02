package com.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 GH: como este proyecto Servicio Item no tiene conexion a la base de datos, sino que es un cliente
 que se comunica con otro microservicio que es Servicio Producto (que ese si tiene la conexion),
 entonces usamos la anotacion EnableAutoConfiguration con el exclude de DataSourceAutoConfiguration
 para que no de error y no exija que el servicio Item tenga una conexion, de ser necesaria una conexion
 por ABC motivo, se tendría que quitar la anotación y configurar la base de datos en este proyecto
 */

@EnableFeignClients
@SpringBootApplication
@EntityScan({
		"springboot.servicio.commons.models.entity"
})
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class
})
public class SpringbootServicioItemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}
}
