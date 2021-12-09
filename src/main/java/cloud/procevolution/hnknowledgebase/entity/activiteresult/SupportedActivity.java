package cloud.procevolution.hnknowledgebase.entity.activiteresult;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.Process;
import lombok.Data;

@Data
public class SupportedActivity {

    private Activity activity;
    private Process process;
    private double percentage;
    private double rating;
}
