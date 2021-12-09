package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.Feature;
import cloud.procevolution.hnknowledgebase.service.FeatureService;
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
public class FeatureApiController {

    private final FeatureService featureService;

    @PostMapping(value = "/api/feature", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Feature> save(@RequestBody Feature feature) throws URISyntaxException {
        var result = featureService.save(feature);
        return ResponseEntity.created(new URI("/public/feature/" + result.getId())).body(result);
    }

    @PostMapping(value = "/api/feature/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        featureService.deleteAll(ids);
    }

}
