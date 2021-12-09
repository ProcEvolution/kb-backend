package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.DataType;
import cloud.procevolution.hnknowledgebase.entity.Interface;
import cloud.procevolution.hnknowledgebase.service.InterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class InterfaceApiController {

    private final InterfaceService interfaceService;

    @PostMapping(value = "/api/interface", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Interface> save(@RequestBody Interface anInterface) throws URISyntaxException {
        var result = interfaceService.save(anInterface);
        return ResponseEntity.created(new URI("/public/interface/" + result.getId())).body(result);
    }

    @PostMapping(value = "/api/interface/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        interfaceService.deleteAll(ids);
    }

}
