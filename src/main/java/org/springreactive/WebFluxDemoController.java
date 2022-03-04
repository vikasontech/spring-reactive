package org.springreactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
class WebFluxDemoController {

  @Autowired
  private ProductRepo productRepo;

  @PostMapping("/create/product")
  public Mono<ProductEntity> createProduct(
      @RequestBody ProductEntity productEntity
  ) {
    productEntity.setId(0);
    return productRepo.save(productEntity);
  }

  @CrossOrigin
  @GetMapping(value = "/product-list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Product> productList() {

    final Stream<Product> streamData = Stream.generate(this::newProductGenerator);


    final Mono<String> data =  Mono.just("something");


    return Flux.fromStream(streamData)
        .take(10)
        .delayElements(Duration.ofSeconds(1)) // demo purpose only.
        ;
  }

  private Product newProductGenerator() {
    final Product product = new Product();
    product.setId(new Random().nextInt());
    product.setDescription("This is product description");
    product.setName("DEMO");
    return product;
  }

  @GetMapping("/product/{id}")
  public Mono<ProductEntity> findProduct(@PathVariable(name = "id") long id) {
    return productRepo.findById(id);
  }

  @GetMapping(value = "/product-list/streams", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<ServerSentEvent<Object>> productListStream() {

    final Stream<Product> stream = Stream.generate(this::newProductGenerator);

    ServerSentEvent.builder()
        .event("new-product").build();

    return Flux.fromStream(stream)
        .map(product ->  ServerSentEvent.builder().event("new-product").data(product).build())
        .take(10)
        .delayElements(Duration.ofSeconds(1)) ;
  }

}

interface ProductRepo extends ReactiveCrudRepository<ProductEntity, Long> { }

@Data
@AllArgsConstructor
@NoArgsConstructor
class Product {
  private long id;
  private String name;
  private String description;
}
