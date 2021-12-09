package cloud.procevolution.hnknowledgebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HnKnowledgebaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnKnowledgebaseApplication.class, args);
    }

}
