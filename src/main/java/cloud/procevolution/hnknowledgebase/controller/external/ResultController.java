package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.activiteresult.ActivityResult;
import cloud.procevolution.hnknowledgebase.entity.activiteresult.Result;
import cloud.procevolution.hnknowledgebase.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;


    @PostMapping(value = "/public/matching/actvitiy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result matchingActivitySide(@RequestBody List<String> ids) throws URISyntaxException {
        return resultService.matchingByActivities(ids);
    }

    @PostMapping(value = "/public/matching/wearable", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ActivityResult matchingWearableSide(@RequestBody List<String> ids) throws URISyntaxException {
        return resultService.matchingByWearable(ids.get(0));
    }
}
