package cloud.procevolution.hnknowledgebase.controller.external;


import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import cloud.procevolution.hnknowledgebase.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/public/activity")
    public List<Activity> findAll() {
        return this.activityService.findAll();
    }

    @GetMapping("/public/activity/{id}")
    public Activity findById(@PathVariable String id) {
        return this.activityService.findById(id);
    }
}
