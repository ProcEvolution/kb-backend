package cloud.procevolution.hnknowledgebase.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;

@Data
@Node
@EqualsAndHashCode
public class Wearable {


    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Relationship(type = "GROUPED_IN", direction = Relationship.Direction.OUTGOING)
    private List<WearableCategory> wearableCategory;

    @Relationship("SUPPORTS")
    @JsonProperty("features")
    private List<Feature> features;

    //private BigDecimal price;

    //private String productURL;

    //private Integer amountBattery;

    //private Integer weight;

    private String iconURL;

    private String description;

    private String name;


}
