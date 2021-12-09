package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.User;
import cloud.procevolution.hnknowledgebase.view.AuthorizeResponse;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizeService {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthorizeResponse authorize(String email, String password) throws RuntimeException {
        AuthorizeResponse authorizeResponse = null;
        try {
            User user = (User) userService.loadUserByUsername(email);
            comparePasswords(user.getPassword(), password);
            authorizeResponse = jwtTokenService.createJwt(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authorizeResponse;
    }

    private void comparePasswords(String storedPassword, String currentPassword) throws RuntimeException {
        if (!this.passwordEncoder.matches(currentPassword, storedPassword)) {
            throw new RuntimeException("wrong password");
        }
    }
}
