package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.service.WearableService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WearableController {

    private final WearableService wearableService;

    @GetMapping(path = "/public/wearable")
    public List<Wearable> show() {
        return this.wearableService.findAll();
    }

    @GetMapping(path = "/public/wearable/icon/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> showIcon(@PathVariable String name) throws IOException {
        try {
            ByteArrayResource resource = this.wearableService.downloadIconByName(name);
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
