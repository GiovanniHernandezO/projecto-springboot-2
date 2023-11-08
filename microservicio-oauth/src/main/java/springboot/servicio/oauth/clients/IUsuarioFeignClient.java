package springboot.servicio.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springboot.servicio.commons.usuarios.models.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface IUsuarioFeignClient {

    @GetMapping("/usuarios/search/buscar-username")
    Usuario findByUsername(@RequestParam String username);

    @PutMapping("/usuarios/{id}")
    Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
}
