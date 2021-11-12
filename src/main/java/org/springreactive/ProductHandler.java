package org.springreactive;

import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ProductHandler {
  Mono<Void>  getProduct(ServerRequest serverRequest);
}

@Service
class ProductHandlerImpl implements ProductHandler {

  @Override
  public Mono<Void> getProduct(ServerRequest serverRequest) {
    return Mono.empty();
  }
}
