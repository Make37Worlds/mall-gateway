package com.example.mallgateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@Import(JwtAuthenticationFilter.class)
public class JwtAuthenticationFilterTests {

    @Autowired
    private WebTestClient webTestClient;

    private String validToken;

    @BeforeEach
    void setUp() {
        validToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY5OTcxOTg2MiwidXNlcklkIjoiMSIsImlhdCI6MTY5OTcxOTg2Mn0.baE-9jJaeeK1pvtZ8LLyGJ9Eu3GDJZTzOjWD6kq2VOm-rq0SW8hfV-xU91VOVMc7xVq-eM9vQxOY-qYSHDFLjtZuiTOmtxFx8QTSarKyqjjFE24CqZ5vcU5x79POf5vqaNd5UQOsZDdtG-TRfkKkG22R7ZilzQq9UQp2T255vXtz-9v4v__MKtGLtnvZbBgDWgtaQwTF3ht1if7RcaUNSTL5hNt-n2M-gtmYqDd3A6Sl6TtSsf5e6JYxgUTS4-HPaAv1QmGcK8Le0Rz_M_5wqrxKlcBU9nhhHrzJshTYqkRS9bythMVxB_Vthb7f0Iv_yMVnV2eLHwgjGZaVaCJ_vg";
    }



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


//    @Test
//    void whenValidToken_thenShouldProceed() {
//        webTestClient.get().uri("/test")
//                .header("Authorization", validToken)
//                .exchange()
//                .expectStatus().isOk();
//    }
//    @Test
//    void whenPostRequestWithValidToken_thenShouldProceed() {
//        webTestClient.post().uri("/testPost")
//                .header("Authorization", validToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue("{ \"key\": \"value\" }") // Replace with your actual request body
//                .exchange()
//                .expectStatus().isOk();
//    }

}
