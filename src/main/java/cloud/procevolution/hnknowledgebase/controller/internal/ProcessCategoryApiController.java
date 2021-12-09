package cloud.procevolution.hnknowledgebase.controller.internal;

import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import cloud.procevolution.hnknowledgebase.service.ProcessCategoryService;
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
public class ProcessCategoryApiController {

    private final ProcessCategoryService processCategoryService;
   // private final String hostString;

    @PostMapping(value = "/api/processcategory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessCategory> save(@RequestBody ProcessCategory processCategoryDto) throws URISyntaxException {
        var result = this.processCategoryService.save(
                processCategoryDto
        );
        return ResponseEntity.created(new URI("/public/processcategory/" + result.getId())).body(result);
    }


    @PostMapping(value = "/api/processcategory/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bulkDelete(@RequestBody List<String> ids) throws URISyntaxException {
        processCategoryService.deleteAllById(ids);
    }
}
