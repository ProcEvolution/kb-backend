package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WearableCategoryRepository extends Neo4jRepository<WearableCategory, String> {

    @Override
    void deleteById(String s);

    @Override
    void deleteAll(Iterable<? extends WearableCategory> iterable);

    @Override
    <S extends WearableCategory> List<S> findAll(Example<S> example);

    @Override
    <S extends WearableCategory> S save(S s);

    @Override
    Optional<WearableCategory> findById(String s);
}
