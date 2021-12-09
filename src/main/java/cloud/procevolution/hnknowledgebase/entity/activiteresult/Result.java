package cloud.procevolution.hnknowledgebase.entity.activiteresult;

import cloud.procevolution.hnknowledgebase.entity.Process;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    private List<SupportedWearable> supportedWearables;
    private Process process;

}
