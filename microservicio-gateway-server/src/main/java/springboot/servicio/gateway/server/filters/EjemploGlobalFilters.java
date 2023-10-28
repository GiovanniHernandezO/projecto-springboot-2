package springboot.servicio.gateway.server.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class EjemploGlobalFilters implements GlobalFilter, Ordered {

    private final Logger LOGGER = LoggerFactory.getLogger(EjemploGlobalFilters.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("Ejecutando filtro PRE");
        LOGGER.info("Modificando el request");

        exchange.getRequest().mutate().headers(h -> h.add("token", "1234567"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            LOGGER.info("Ejecutando filtro POST");
            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);

            LOGGER.info("obteniendo cabacera request TOKEN");
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
                exchange.getResponse().getHeaders().add("token", valor);
            });
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
