package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import cloud.procevolution.hnknowledgebase.service.WearableCategoryService;
import cloud.procevolution.hnknowledgebase.view.dto.WearableCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WearableCategoryController {

    private final WearableCategoryService wearableCategoryService;

    @GetMapping(path = "/public/wearablecategory")
    public List<WearableCategoryDto> show() {
        return this.wearableCategoryService.findAll();
    }

    @GetMapping(path = "/public/wearablecategory/{id}")
    public WearableCategoryDto findById(@PathVariable String id) {
        return this.wearableCategoryService.findById(id);
    }

    @GetMapping(path = "/public/wearablecategory/icon/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> showIcon(@PathVariable String name) throws IOException {
        try {
            ByteArrayResource resource = this.wearableCategoryService.downloadIconByName(name);
            String mimeType = URLConnection.guessContentTypeFromName(name);
            return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.valueOf(mimeType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}