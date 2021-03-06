package cloud.procevolution.hnknowledgebase.configuration;

import cloud.procevolution.hnknowledgebase.entity.Role;
import cloud.procevolution.hnknowledgebase.entity.User;
import cloud.procevolution.hnknowledgebase.repository.UserRepository;
import cloud.procevolution.hnknowledgebase.service.JwkService;
import cloud.procevolution.hnknowledgebase.view.dto.UserDto;
import cloud.procevolution.hnknowledgebase.view.mapping.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Component
public class LoadJwkConfiguration {

    private final JwkService jwkService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${data.dir.images}")
    private String imageDir;

    @Value(value = "${data.dir}")
    private String dataDir;

    @PostConstruct
    public void onStartUp() throws IOException, JOSEException {
        File dataDirectory = Path.of(dataDir).toFile();
        if (!dataDirectory.exists()) {
            Files.createDirectory(Path.of(dataDir));
            Files.createDirectory(Path.of(imageDir));
        } else {
            if (dataDirectory.isDirectory()) {
                if (!Path.of(imageDir).toFile().exists()) {
                    Files.createDirectory(Path.of(imageDir));
                }
            } else {
                throw new RuntimeException("cannot create imagedir");
            }
        }

        File keys = Paths.get("keys.json").toFile();
        if (keys.exists()) {
            jwkService.loadKeys();
        } else {
            jwkService.generateKeys();
        }

        /// inital admin user
        User user = new User();
        user.setEmail("admin@amin.com");
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        user.setPassword(this.passwordEncoder.encode("root"));
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");
        user.setRoles(Set.of(
                role1, role2
        ));
        this.userRepository.save(user);
    }

}
