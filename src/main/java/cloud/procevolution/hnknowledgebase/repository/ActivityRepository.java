package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends Neo4jRepository<Activity, String> {

    @Override
    List<Activity> findAll();

    @Override
    Optional<Activity> findById(String s);

    @Override
    void deleteAll(Iterable<? extends Activity> iterable);

    @Override
    <S extends Activity> S save(S s);
}
