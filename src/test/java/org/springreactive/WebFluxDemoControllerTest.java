package org.springreactive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;


@ExtendWith(MockitoExtension.class)
@WebFluxTest(WebFluxDemoController.class)
class WebFluxDemoControllerTest {
  @Autowired
  WebTestClient client;
  @MockBean
  private ProductRepo productRepo;

  @Test
  @DisplayName("Get Product success")
  void test__get_product_uri__success() {

    Mockito.when(productRepo.findById(1L))
        .thenReturn(Mono.just(ProductEntity.builder()
            .id(1L)
            .name("Some name")
            .description("some description").build()));

    client.get()
        .uri("/api/product/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(ProductEntity.class)
        .value(it -> {
          assert it.getId() == 1L;
        });
    Mockito.verify(productRepo).findById(1L);
  }

  @Test
  @DisplayName("Product list success")
  void test__productList_uri__success() {

    client.get()
        .uri("/api/product-list")
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBodyList(Product.class);
  }

  @Test
  @DisplayName("Create Product success")
  void test__create_product__success() {

    final ProductEntity productEntity = ProductEntity.builder()
        .name("vikas")
        .description("some description")
        .build();

    client.post()
        .uri("/api/create/product")
        .body(BodyInserters.fromValue(productEntity))
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(ProductEntity.class);
  }

}