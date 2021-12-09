package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Override
    <S extends User> List<S> findAll(Example<S> example);

    @Override
    <S extends User> S save(S s);
}
