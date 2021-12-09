package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.Feature;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FeatureRepository extends Neo4jRepository<Feature,String> {
}
