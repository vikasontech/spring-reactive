package org.springreactive.cofigs;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springreactive.ProductEntity;
import org.springreactive.ProductHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AbstractWebFluxConfigurer implements WebFluxConfigurer{

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    corsRegistry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("*")
        .maxAge(3600);
  }

}

@Controller
class SomeController {
  @Bean
  public RouterFunction<ServerResponse> getProductRoute(ProductHandler productHandler) {
    return route(GET("/students"),
        request ->
            ok()
                .body(productHandler.getProduct(request), ProductEntity.class));
  }

}