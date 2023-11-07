package springboot.servicio.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.servicio.commons.usuarios.models.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface IUsuarioFeignClient {

    @GetMapping("/usuarios/search/buscar-username")
    Usuario findByUsername(@RequestParam String username);
}
