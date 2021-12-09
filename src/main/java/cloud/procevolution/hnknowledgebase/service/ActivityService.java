package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.repository.ActivityRepository;
import cloud.procevolution.hnknowledgebase.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ProcessRepository processRepository;

    public List<Activity> findAll() {
        var list = activityRepository.findAll();
        var processes = processRepository.findAll();
        for (Activity activity : list) {

            processes.stream().filter(process -> process.getActivity() != null).filter(process -> process.getActivity().contains(activity)).findFirst().ifPresent(process -> {
                process.setActivity(null);
                activity.setProcess(process);
            });

        }
        return list;
    }

    public Activity findById(String id) {
        return activityRepository.findById(id).orElseThrow();
    }

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteAllById(List<String> ids) {
        ids.forEach(activityRepository::deleteById);
    }
}
