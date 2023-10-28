package springboot.servicio.gateway.server.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {
    private final Logger LOGGER = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    @Override
    public GatewayFilter apply(Configuracion config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            LOGGER.info("Ejecutando filtro PRE Gateway EjemploGatewayFilterFactory: " + config.mensaje);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
                });
                LOGGER.info("Ejecutando filtro POST Gateway EjemploGatewayFilterFactory: " + config.mensaje);
            }));
        }, 2);
    }

    @Override
    public String name() {
        return "FilterCookie";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
    }

    public static class Configuracion {
        private String mensaje;
        private String cookieNombre;
        private String cookieValor;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCookieNombre() {
            return cookieNombre;
        }

        public void setCookieNombre(String cookieNombre) {
            this.cookieNombre = cookieNombre;
        }

        public String getCookieValor() {
            return cookieValor;
        }

        public void setCookieValor(String cookieValor) {
            this.cookieValor = cookieValor;
        }
    }
}
