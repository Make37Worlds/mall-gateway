package com.example.mallgateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JwtAuthenticationFilter.class)
public class JwtAuthenticationFilterTests {

    @Autowired
    private WebTestClient webTestClient;

    private String validToken;

    @BeforeEach
    void setUp() {
        validToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY5OTc2NTMxNCwidXNlcklkIjoiMSIsImlhdCI6MTY5OTc2MTcxNH0.JEZMJ_SyrkNGunmK9L0RaMQfiLfDgPYrXctTsLrzxJ2Jt3iSxvlvs_v-ozYOrxSCgqUXHpykzgS_jq5BxuvzyoiOK_huBdknT2l4CnRzODCL5xq6OLh7miItVRkYafi5W8nyVzqpYzcUcrKuwX10H5QwlocwG_r92ffkv7s-csZTVD8kZh_Ihp-8CiIYx36MNrOxWYZAWyU1Gw-PsS8V2gXysIUAVwvCbKiTVbuCApkmYpT5q7b29o2mjB7eLwiWTv0VAFlCKlaJoAYi-4pDeFe74_We0Tkl2xvP4tifld0UOI0iMmz8bE0jjGIsasVjHz0dYCgN3CrnzpkPBW5fvg";
    }



    @Test
    void whenInvalidToken_thenShouldReturnUnauthorized() {
        webTestClient.get().uri("/api/mallmember/test")
                .header("Authorization", "invalid_token")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenNoToken_thenShouldReturnUnauthorized() {
        webTestClient.get().uri("/api/mallmember/test")
                .exchange()
                .expectStatus().isUnauthorized();
    }

//    @Test
//    void whenRequestToPermitAllPath_thenShouldProceedWithoutToken() {
//        webTestClient.get().uri("/api/mallmember/login")
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//
//    @Test
//    void whenValidToken_thenShouldProceed() {
//        webTestClient.get().uri("/api/mallmember/test")
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
