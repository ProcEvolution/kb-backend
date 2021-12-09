package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Feature;
import cloud.procevolution.hnknowledgebase.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    public Feature save(Feature feature) {
        return featureRepository.save(feature);
    }

    public void deleteAll(List<String> ids) {
        ids.forEach(featureRepository::deleteById);
    }
}
