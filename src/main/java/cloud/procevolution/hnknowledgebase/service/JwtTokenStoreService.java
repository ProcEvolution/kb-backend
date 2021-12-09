package cloud.procevolution.hnknowledgebase.service;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class JwtTokenStoreService {

    private final JwkService jwkService;
    private final ConcurrentHashMap<String, TokenHolder> tokenStore = new ConcurrentHashMap<>();

    public RSAKey keyFromJTI(String jti) throws IOException {
        String kid = tokenStore.get(jti).getKid();
        return jwkService.getByKid(kid);
    }

    public String put(String jti, String kid, String token) throws NoSuchAlgorithmException {
        String refreshToken = generateRefreshToken();
        tokenStore.put(
                jti,
                new TokenHolder(token, refreshToken, kid)
        );
        return refreshToken;
    }

    private String generateRefreshToken() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return bytesToHex(digest.digest(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class TokenHolder {

        private String token;
        private String refreshToken;
        private String kid;
    }
}
