package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityApiController {

    private final ActivityService activityService;

    @PostMapping(value = "/api/activity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> save(@RequestBody Activity activity) throws URISyntaxException {
        var result = activityService.save(activity);
        return ResponseEntity.created(new URI("/public/activity/" + result.getId())).body(result);
    }

    @PostMapping(value = "/api/activity/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        activityService.deleteAllById(ids);
    }
}
