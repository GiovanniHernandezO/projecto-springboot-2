package springboot.servicio.oauth.services;

import springboot.servicio.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {

    Usuario findByUsername(String username);
}
