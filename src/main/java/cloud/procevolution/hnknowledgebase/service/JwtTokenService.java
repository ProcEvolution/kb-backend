package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.User;
import cloud.procevolution.hnknowledgebase.view.AuthorizeResponse;
import cloud.procevolution.hnknowledgebase.view.dto.UserDto;
import cloud.procevolution.hnknowledgebase.view.mapping.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwkService jwkService;
    //private final JwtTokenStoreService jwtTokenStoreService;
    private final HttpServletRequest httpServletRequest;
    private final UserMapper userMapper;

    public AuthorizeResponse createJwt(User user) throws JOSEException, IOException, NoSuchAlgorithmException, ParseException {
        RSAKey rsaKey = jwkService.random();
        JWSSigner signer = new RSASSASigner(rsaKey);
        final Date expirationDate = new Date(new Date().getTime() + 60 * 1000 * 10000);
        String hostPath = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
        String jti = UUID.randomUUID().toString();
        Date now = new Date();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(hostPath)
                .subject("user")
                .audience(List.of("http://localhost:4200"))
                .jwtID(jti)
                .claim("user", this.userMapper.userToUserDto(user))
                .expirationTime(expirationDate)
                .notBeforeTime(now)
                .issueTime(now)
                .build();

        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        EncryptedJWT jwt = new EncryptedJWT(new JWEHeader.Builder(header).keyID(rsaKey.getKeyID()).build(), claimsSet);

        RSAEncrypter encrypter = new RSAEncrypter(rsaKey);

        jwt.encrypt(encrypter);
/*        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(rsaKey.getKeyID())
                        .build(),
                claimsSet);

        signedJWT.sign(signer);

        String token = signedJWT.serialize();
        String test = signedJWT.getJWTClaimsSet().getJWTID();*/
/*        String refreshToken = this.jwtTokenStoreService.put(
                signedJWT.getJWTClaimsSet().getJWTID(),
                rsaKey.getKeyID(),
                token
        );*/

        //User usesr = fromToken(jwt.serialize()).get();
        AuthorizeResponse authorizeResponse = new AuthorizeResponse();
        authorizeResponse.setAccessToken(jwt.serialize());
        authorizeResponse.setExpiresIn((int) expirationDate.getTime());
        authorizeResponse.setRefreshToken("refreshToken");
        return authorizeResponse;
    }

    public boolean validate(String token){
        try {
            EncryptedJWT.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> fromToken(String token) throws JsonProcessingException {
        EncryptedJWT encryptedJWT = this.decodeToken(token);
        UserDto result = new ObjectMapper().readValue(((JSONObject)encryptedJWT.getPayload().toJSONObject().get("user")).toJSONString(), UserDto.class);
        return Optional.ofNullable(userMapper.userDtoToUser(result));
    }

    private EncryptedJWT decodeToken(String token) {
        try {
            EncryptedJWT jwt = EncryptedJWT.parse(token);
            RSAKey rsaKey = this.jwkService.getByKid(jwt.getHeader().getKeyID());
            RSADecrypter decrypter = new RSADecrypter(rsaKey);
            jwt.decrypt(decrypter);
            return jwt;
        } catch (ParseException | IOException | JOSEException e) {
            throw new RuntimeException("can not decode jwt");
        }
    }
}
