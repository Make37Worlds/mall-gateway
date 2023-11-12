package com.example.mallgateway.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
@Component
public class JwtUtils {

    // 从字符串加载公钥
    private static RSAPublicKey getPublicKey(String publicKeyFile) throws Exception {
        URL privateKeyUrl = ClassName.class.getClassLoader().getResource(publicKeyFile);
        String privateKeyContent = null;
        if (privateKeyUrl != null) {
            try (InputStream privateKeyStream = privateKeyUrl.openStream()) {

                privateKeyContent = new String(privateKeyStream.readAllBytes(), StandardCharsets.UTF_8);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String publicKeyPEM = privateKeyContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+","");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    // 验证Token
    public static DecodedJWT verifyToken(String token, String publicKeyFilename) {
        try {
            RSAPublicKey publicKey = getPublicKey(publicKeyFilename);

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            return JWT.require(algorithm).build().verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
