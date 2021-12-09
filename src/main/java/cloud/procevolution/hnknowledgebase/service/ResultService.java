package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.Feature;
import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.entity.activiteresult.ActivityResult;
import cloud.procevolution.hnknowledgebase.entity.activiteresult.Result;
import cloud.procevolution.hnknowledgebase.entity.activiteresult.SupportedActivity;
import cloud.procevolution.hnknowledgebase.entity.activiteresult.SupportedWearable;
import cloud.procevolution.hnknowledgebase.repository.ActivityRepository;
import cloud.procevolution.hnknowledgebase.repository.ProcessRepository;
import cloud.procevolution.hnknowledgebase.repository.WearableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final ActivityRepository activityRepository;
    private final WearableRepository wearableRepository;
    private final ProcessRepository processRepository;


    // TODO: needs to be refactored
    public Result matchingByActivities(List<String> activityIds) {
        final List<Activity> activities = activityRepository.findAllById(activityIds);
        final Optional<Process> processResult = processRepository.findAll().stream().filter(process -> process.getActivity().contains(activities.get(0))).findFirst();
        final List<Wearable> wearables = wearableRepository.findAll();
        final List<SupportedWearable> supportedWearables = new ArrayList<>();
        for (Wearable wearable : wearables) {
            final SupportedWearable supportedWearable = new SupportedWearable();
            supportedWearable.setWearable(wearable);
            List<Activity> supportedActivities = new ArrayList<>(); //wearableSupportedActivity(wearable, activities);
            for (Activity activity : activities) {
                int featureCount = 0;
                for (Feature feature : activity.getFeatures()) {
                    for (Feature wearableFeature : wearable.getFeatures()) {
                        if (wearableFeature.equals(feature)) {
                            featureCount++;
                        }
                    }
                }
                if (featureCount == activity.getFeatures().size()) {
                    supportedActivities.add(activity);
                }
            }
            if (supportedActivities.size() > 0) {
                supportedWearable.setActivities(supportedActivities);
                double percentage = ((double) supportedActivities.size() / activities.size()) * 100;
                supportedWearable.setPercentage(percentage);
                supportedWearable.setRating(percentageToRating(percentage));
                supportedWearables.add(supportedWearable);
            }
        }
        final Result result = new Result();
        result.setSupportedWearables(supportedWearables);
        result.setProcess(processResult.get());
        return result;
    }

    // TODO: needs to be refactored
    public ActivityResult matchingByWearable(String wearableIds) {
        final Wearable wearable = wearableRepository.findById(wearableIds).get();
        final List<Feature> allRequiredFeatures = wearable.getFeatures();
        final List<SupportedActivity> supportedActivities = new ArrayList<>();
        final ActivityResult result = new ActivityResult();

        List<Activity> filteredActivities = activityRepository.findAll()
                .stream()
                .filter(activity -> {
                    for (Feature feature : activity.getFeatures()) {
                        if (allRequiredFeatures.contains(feature)) {
                            return true;
                        }
                    }
                    return false;
                }).toList();

        filteredActivities.forEach(activity -> {
            final SupportedActivity supportedActivity = new SupportedActivity();
            final Optional<Process> processResult = processRepository.findAll().stream().filter(process -> process.getActivity().contains(activity)).findFirst();
            processResult.ifPresent(activity::setProcess);
            processResult.ifPresent(supportedActivity::setProcess);
            supportedActivity.setActivity(activity);
            final List<Feature> supportedFeatures = new ArrayList<>();
            wearable.getFeatures().forEach(feature -> {
                if (activity.getFeatures().contains(feature)) {
                    supportedFeatures.add(feature);
                }
            });
            double percentage = ((double) supportedFeatures.size() / wearable.getFeatures().size()) * 100;
            supportedActivity.setPercentage(percentage);
            supportedActivity.setRating(percentageToRating(percentage));
            supportedActivities.add(supportedActivity);
        });

        result.setWearable(wearable);
        result.setSupportedActivity(supportedActivities);
        Map<String, List<SupportedActivity>> proccesesListMap = new HashMap<>();
        for (SupportedActivity supportedActivity : result.getSupportedActivity()) {
            if (supportedActivity.getProcess() != null) {
                List<SupportedActivity> tmp;
                if (proccesesListMap.containsKey(supportedActivity.getProcess().getId())) {
                    tmp = proccesesListMap.get(supportedActivity.getProcess().getId());
                } else {
                    tmp = new ArrayList<>();
                }
                tmp.add(supportedActivity);
                proccesesListMap.put(supportedActivity.getProcess().getId(), tmp);
            }
        }
        for (SupportedActivity supportedActivity : result.getSupportedActivity()) {
            double percentage = ((double) proccesesListMap.get(supportedActivity.getProcess().getId()).size() / proccesesListMap.get(supportedActivity.getProcess().getId()).get(0).getProcess().getActivity().size()) * 100;
            supportedActivity.setPercentage(percentage);
            supportedActivity.setRating(percentageToRating(percentage));
        }

        return result;
    }

    private double percentageToRating(double percentage) {
        if (percentage >= 99) {
            return 5;
        } else if (percentage > 80) {
            return 4.5;
        } else if (percentage > 70) {
            return 4;
        } else if (percentage > 60) {
            return 3.5;
        } else if (percentage > 50) {
            return 2.5;
        } else if (percentage > 40) {
            return 2;
        } else if (percentage > 30) {
            return 1.5;
        } else if (percentage > 20) {
            return 1;
        } else if (percentage > 10) {
            return 0.5;
        } else {
            return 0;
        }
    }

    private List<Activity> wearableSupportedActivity(Wearable wearable, List<Activity> activities) {
        List<Activity> result = new ArrayList<>();
        for (Activity activity : activities) {
            if (wearableFullfillActivity(wearable, activity)) {
                result.add(activity);
            }
        }
        return result;
    }

    private boolean wearableFullfillActivity(Wearable wearable, Activity activity) {
        return wearable.getFeatures().containsAll(activity.getFeatures());
    }

}
