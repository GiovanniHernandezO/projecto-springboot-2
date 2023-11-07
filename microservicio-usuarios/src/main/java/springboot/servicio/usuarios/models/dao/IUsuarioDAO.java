package springboot.servicio.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import springboot.servicio.commons.usuarios.models.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

    @RestResource(path = "buscar-username")
    Usuario findByUsername(@Param("username") String username);

    @RestResource(path = "obtener-por-query")
    @Query(value = "select u from Usuario u where u.username = ?1")
    Usuario obtenerPorUsername(@Param("nombre-usuario") String username);

    @RestResource(path = "obtener-por-query-nativa")
    @Query(value = "select * from usuarios u where u.username = ?1", nativeQuery = true)
    Usuario obtenerPorUsernameFormaNativaSQL(@Param("nombre-usuario-nativo")String username);
}
