package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.service.ProcessService;
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
public class ProcessApiController {

    private final ProcessService processService;

    @PostMapping(value = "/api/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Process> save(@RequestBody Process process) throws URISyntaxException {
        var result = this.processService.save(process);
        return ResponseEntity.created(new URI("/public/process/" + result.getId())).body(result);
    }

    @PostMapping(value = "/api/process/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        processService.deleteAllById(ids);
    }
}
