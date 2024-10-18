package org.okten.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.inventoryservice.dto.ProductAvailability;
import org.okten.inventoryservice.dto.ProductDto;
import org.okten.inventoryservice.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTemplateProductService implements ProductService {

    private final Supplier<String> tokenProvider;

    @Override
    public void updateProductAvailability(Long productId, ProductAvailability value) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        // Access token propagation
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.get());

        ProductDto body = ProductDto.builder()
                .availability(value)
                .build();
        HttpEntity<ProductDto> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<ProductDto> response = restTemplate
                .exchange(
                        "http://localhost:8080/products/{productId}",
                        HttpMethod.PUT,
                        httpEntity,
                        ProductDto.class,
                        productId);

        log.info("Product availability updated. Status: {}. Body: {}", response.getStatusCode(), response.getBody());
    }
}
