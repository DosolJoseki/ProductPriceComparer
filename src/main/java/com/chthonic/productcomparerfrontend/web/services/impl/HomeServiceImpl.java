package com.chthonic.productcomparerfrontend.web.services.impl;

import com.chthonic.productcomparerfrontend.web.dto.Product;
import com.chthonic.productcomparerfrontend.web.services.HomeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    private final WebClient webClient;

    public HomeServiceImpl() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        Mono<List<Product>> productsMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get-product")
                        .queryParam("productName", productName)
                        .build()
                )
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList();

        return productsMono.block();
    }
}
