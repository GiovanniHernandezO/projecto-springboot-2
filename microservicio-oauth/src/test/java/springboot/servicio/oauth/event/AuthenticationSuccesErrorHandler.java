package springboot.servicio.oauth.event;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import springboot.servicio.commons.usuarios.models.entity.Usuario;
import springboot.servicio.oauth.services.IUsuarioService;

@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccesErrorHandler.class);
    private static final Integer INTENTOS_MAXIMOS = 3;
    private static final Integer INTENTOS_INCREMENTO = 1;
    private static final Integer INTENTO_CERO = 0;

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails user = (UserDetails) authentication.getPrincipal();
        LOGGER.info(String.format("Success login de %s", user.getUsername()));

        Usuario usuario = usuarioService.findByUsername(authentication.getName());
        if (usuario != null && usuario.getIntentos() != null && usuario.getIntentos() > INTENTO_CERO) {
            usuario.setIntentos(INTENTO_CERO);
            usuarioService.update(usuario, usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        LOGGER.error(String.format("Error en el login - %s", e.getMessage()));

        try {
            Usuario usuario = usuarioService.findByUsername(authentication.getName());
            if (usuario.getIntentos() == null) {
                usuario.setIntentos(0);
            }

            LOGGER.info(String.format("Intentos actual es de %d", usuario.getIntentos()));
            usuario.setIntentos(usuario.getIntentos() + INTENTOS_INCREMENTO);
            LOGGER.info(String.format("Intentos después es de %d", usuario.getIntentos()));
            
            if (usuario.getIntentos() >= INTENTOS_MAXIMOS) {
                LOGGER.info(String.format("El usuario %s desactivado por máximo de intentos (%d)", usuario.getUsername(), INTENTOS_MAXIMOS));
                usuario.setEnabled(Boolean.FALSE);
            }
            usuarioService.update(usuario, usuario.getId());
        } catch(FeignException fe) {
            LOGGER.error(String.format("El usuario %s no existe en el sistema.", authentication.getName()));
            LOGGER.error(fe.getMessage());
        }
    }
}
