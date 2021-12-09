package cloud.procevolution.hnknowledgebase.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Node
@EqualsAndHashCode
public class Process {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String name;

    @Relationship(type = "GROUPED_IN", direction = Relationship.Direction.OUTGOING)
    private List<ProcessCategory> processCategory;

    @JsonProperty("activities")
    @Relationship(type = "CONSIST_OF")
    private List<Activity> activity;

}
