package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WearableRepository extends Neo4jRepository<Wearable, String> {

    @Override
    <S extends Wearable> List<S> findAll(Example<S> example);

    @Override
    <S extends Wearable> S save(S s);

    List<Wearable> findByWearableCategory(WearableCategory wearableCategory);
}
