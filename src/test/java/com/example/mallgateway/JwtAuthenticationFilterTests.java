package com.example.mallgateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@Import(JwtAuthenticationFilter.class)
public class JwtAuthenticationFilterTests {

    @Autowired
    private WebTestClient webTestClient;

    private String validToken;

    @BeforeEach
    void setUp() {
        // Initialize your valid token here
        // This should be a token that will pass the verification in your filter
        validToken = "your_valid_test_token";
    }

//    @Test
//    void whenValidToken_thenShouldProceed() {
//        webTestClient.get().uri("/test")
//                .header("Authorization", validToken)
//                .exchange()
//                .expectStatus().isOk();
//    }

    @Test
    void whenInvalidToken_thenShouldReturnUnauthorized() {
        webTestClient.get().uri("/test")
                .header("Authorization", "invalid_token")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenNoToken_thenShouldReturnUnauthorized() {
        webTestClient.get().uri("/test")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenRequestToPermitAllPath_thenShouldProceedWithoutToken() {
        webTestClient.get().uri("/login")
                .exchange()
                .expectStatus().isOk();
    }
}
