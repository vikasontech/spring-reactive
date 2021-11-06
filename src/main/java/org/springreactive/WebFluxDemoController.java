package org.springreactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
class WebFluxDemoController{

  @Autowired
  private ProductRepo productRepo;

  @GetMapping(value = "/product-list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Product> productList(){
    final Stream<Product> stream = Stream.generate(() -> {
      final Product product = new Product();
      product.setId(new Random().nextInt());
      product.setDescription("This is product description");
      product.setName("I am some product");
      return product;
    });

    return Flux.fromStream(stream)
        .take(10)
        .delayElements(Duration.ofSeconds(1));
  }

  @PostMapping("/create/product")
  public Mono<ProductEntity> createProduct(
      @RequestBody ProductEntity productEntity
  ) {
    productEntity.setId(0);
    return productRepo.save(productEntity);
  }

  @GetMapping("/product/{id}")
  public Mono<ProductEntity> findProduct( @PathVariable(name = "id") long id ) {
    return productRepo.findById(id);
  }

}

interface ProductRepo extends ReactiveCrudRepository<ProductEntity, Long> {
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
class ProductEntity {
  @Id
  private long id;
  private String name;
  private String description;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Product {
  private long id;
  private String name;
  private String description;
}
