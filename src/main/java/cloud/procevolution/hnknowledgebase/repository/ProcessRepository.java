package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends Neo4jRepository<Process, String> {

    @Override
    List<Process> findAll();

    @Override
    Optional<Process> findById(String id);

    @Override
    <S extends Process> S save(S s);

    Optional<Process> findByActivity(Activity activity);

    @Override
    void deleteAll(Iterable<? extends Process> iterable);

    List<Process> findAllByProcessCategory(ProcessCategory processCategory);
}
