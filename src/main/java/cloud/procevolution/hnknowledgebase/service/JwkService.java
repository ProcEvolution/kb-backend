package cloud.procevolution.hnknowledgebase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

@Service
public class JwkService {

    private final ObjectMapper objectMapper;
    //private final CacheManager cacheManager;
    private final CaffeineCache cache; //= (CaffeineCache) Objects.requireNonNull(cacheManager.getCache("keys"));

    public JwkService(ObjectMapper objectMapper, CacheManager cacheManager) {
        this.objectMapper = objectMapper;
       //this.cacheManager = cacheManager;
        this.cache = (CaffeineCache) Objects.requireNonNull(cacheManager.getCache("keys"));
    }


    public RSAKey random() throws IOException {
        checkForEmptyCache();
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = cache.getNativeCache();
        List<String> items = nativeCache.asMap().keySet().stream().map(o -> (String) o).toList();
        Random random = new Random();
        String randomKey = items.get(random.nextInt(items.size()));
        return cache.get(randomKey, RSAKey.class);
    }

    private void checkForEmptyCache() throws IOException {
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = cache.getNativeCache();
        List<String> items = nativeCache.asMap().keySet().stream().map(o -> (String) o).toList();
        if (items.isEmpty()) {
            loadKeys();
        }
    }

    public RSAKey getByKid(String kId) throws IOException {
        checkForEmptyCache();
        return cache.get(kId, RSAKey.class);
    }

    public void storeToCache(RSAKey rsaKey) {
        cache.put(rsaKey.getKeyID(), rsaKey);
    }

    public void loadKeys() throws IOException {
        List<?> keys = this.objectMapper.readValue(Paths.get("keys.json").toFile(), List.class);
        keys.stream().map(o -> {
            try {
                return RSAKey.parse((Map<String, Object>) o);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(this::storeToCache);
    }

    public void generateKeys() throws JOSEException, IOException {
        List<Map<String, Object>> keyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RSAKey rsaJWK = new RSAKeyGenerator(2048)
                    .keyID(UUID.randomUUID().toString())
                    .generate();
            keyList.add(rsaJWK.toJSONObject());
            storeToCache(rsaJWK);
        }
        objectMapper.writeValue(Paths.get("keys.json").toFile(), keyList);
    }
}
