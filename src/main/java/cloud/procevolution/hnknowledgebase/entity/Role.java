package cloud.procevolution.hnknowledgebase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Node
@Data
@EqualsAndHashCode
public class Role {

    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String name;
}
