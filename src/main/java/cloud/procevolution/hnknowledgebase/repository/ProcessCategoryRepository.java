package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessCategoryRepository extends Neo4jRepository<ProcessCategory, String> {

    @Override
    List<ProcessCategory> findAll();

    @Override
    Optional<ProcessCategory> findById(String s);

    @Override
    <S extends ProcessCategory> S save(S s);

    @Override
    void deleteAll(Iterable<? extends ProcessCategory> iterable);

    @Override
    void deleteById(String s);
}
