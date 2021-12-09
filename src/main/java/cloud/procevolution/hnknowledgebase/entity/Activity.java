package cloud.procevolution.hnknowledgebase.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Node
public class Activity {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String name;

   // @JsonProperty("requiredFeatures")
    @Relationship("REQUIRES")
    private List<Feature> features;

    @Transient
    private Process process;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(features, activity.features) && Objects.equals(process, activity.process);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, features, process);
    }
}
