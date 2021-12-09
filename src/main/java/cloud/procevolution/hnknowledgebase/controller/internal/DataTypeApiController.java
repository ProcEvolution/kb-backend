package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.DataType;
import cloud.procevolution.hnknowledgebase.service.DataTypeService;
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
public class DataTypeApiController {

    private final DataTypeService dataTypeService;

    @PostMapping(value = "/api/datatype", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataType> save(@RequestBody DataType dataType) throws URISyntaxException {
        var result = dataTypeService.save(dataType);
        return ResponseEntity.created(new URI("/public/datatype/" + result.getId())).body(result);
    }

    @PostMapping(value = "/api/datatype/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        dataTypeService.deleteAllById(ids);
    }

}
