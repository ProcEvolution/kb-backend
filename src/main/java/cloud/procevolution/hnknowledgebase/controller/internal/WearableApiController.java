package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.service.WearableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WearableApiController {

    private final WearableService wearableService;

    @PostMapping(path = "/api/wearable")
    public Wearable save(@RequestBody Wearable wearableDto) {
        return this.wearableService.save(wearableDto);
    }

    @PostMapping(path = "/api/wearable/{id}/icon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Wearable uploadIcon(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        return this.wearableService.uploadFileById(id, file);
    }

    @PostMapping(value = "/api/wearable/delete")
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        wearableService.deleteAllById(ids);
    }
}
