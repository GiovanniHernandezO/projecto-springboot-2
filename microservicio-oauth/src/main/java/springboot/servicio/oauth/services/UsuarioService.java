package springboot.servicio.oauth.services;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.servicio.commons.usuarios.models.entity.Usuario;
import springboot.servicio.oauth.clients.IUsuarioFeignClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioFeignClient usuarioFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioFeignClient.findByUsername(username);
            List<GrantedAuthority> authorities = usuario
                    .getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                    .peek(authority -> LOGGER.info(String.format("Role: %s", authority.getAuthority())))
                    .collect(Collectors.toList());

            LOGGER.info(String.format("Usuario autenticado: %s", username));

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getEnabled(),
                    true,
                    true,
                    true,
                    authorities);
        } catch (FeignException fe) {
            String mensaje = String.format("Error en el login, no existe el usuario %s en el sistema", username);
            LOGGER.error(mensaje);
            throw new UsernameNotFoundException(mensaje);
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioFeignClient.findByUsername(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return usuarioFeignClient.update(usuario, id);
    }
}
