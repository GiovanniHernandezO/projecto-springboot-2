package springboot.servicio.usuarios.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import springboot.servicio.commons.usuarios.models.entity.Role;
import springboot.servicio.commons.usuarios.models.entity.Usuario;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        /*
        GH: esta configuracion permite mostrar los identificadores de las entidades/tablas en el JSON
        por defecto no muestra el ID pero gracias al exposeIdsFor se pueden mostrar
        * */
        config.exposeIdsFor(Usuario.class, Role.class);
    }
}
