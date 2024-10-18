package org.okten.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.okten.inventoryservice.productapi.ApiClient;
import org.okten.inventoryservice.productapi.client.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ProductApiConfig {

    private final Supplier<String> tokenProvider;

    // OPTIONAL
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors((request, body, execution) -> {
                    request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.get());
                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    public ApiClient productApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBearerToken(tokenProvider);
        return apiClient;
    }

    @Bean
    public ProductApi productApi() {
        return new ProductApi(productApiClient());
    }
}
