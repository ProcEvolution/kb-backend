package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import cloud.procevolution.hnknowledgebase.service.WearableCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WearableCategoryApiController {

    private final WearableCategoryService wearableCategoryService;

    @PostMapping(path = "/api/wearablecategory", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WearableCategory save(@RequestBody WearableCategory wearableCategoryDto) {
        return this.wearableCategoryService.save(
                wearableCategoryDto
        );
    }

    @PostMapping(path = "/api/wearablecategory/{id}/icon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WearableCategory uploadIcon(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        return this.wearableCategoryService.uploadFileById(id, file);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/api/wearablecategory/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws RuntimeException {
        this.wearableCategoryService.bulkDelete(ids);
    }

    @PostMapping(path = "/api/wearablecategory/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WearableCategory updateWearableCategory(@PathVariable String id, @RequestBody WearableCategory wearableCategoryDto) {
        return this.wearableCategoryService.update(
                id,
                wearableCategoryDto
        );
    }
}
