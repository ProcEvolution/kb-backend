package cloud.procevolution.hnknowledgebase.entity.activiteresult;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.Wearable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupportedWearable {

    private Wearable wearable;
    private List<Activity> activities;
    private double percentage;
    private double rating;
}
