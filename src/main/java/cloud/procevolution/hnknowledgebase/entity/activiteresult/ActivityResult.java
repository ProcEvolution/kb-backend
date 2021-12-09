package cloud.procevolution.hnknowledgebase.entity.activiteresult;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import lombok.Data;

import java.util.List;

@Data
public class ActivityResult {

    private Wearable wearable;
    private List<SupportedActivity> supportedActivity;
}
