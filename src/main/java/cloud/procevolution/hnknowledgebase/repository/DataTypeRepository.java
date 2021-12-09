package cloud.procevolution.hnknowledgebase.repository;

import cloud.procevolution.hnknowledgebase.entity.DataType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends Neo4jRepository<DataType, String> {

    @Override
    <S extends DataType> S save(S s);
}
