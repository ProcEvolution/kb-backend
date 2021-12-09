package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import cloud.procevolution.hnknowledgebase.service.ProcessCategoryService;
import cloud.procevolution.hnknowledgebase.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProcessCategoryController {

    private final ProcessCategoryService processCategoryService;
    private final ProcessService processService;

    @GetMapping("/public/processcategory")
    public List<ProcessCategory> findAll() {
        return this.processCategoryService.findAll();
    }

    @GetMapping("/public/processcategory/{id}")
    public ProcessCategory findById(@PathVariable String id) {
        return this.processCategoryService.findById(id);
    }

    @GetMapping("/public/processcategory/{id}/process")
    public List<Process> findAllProcesses(@PathVariable String id) {
        return processService.findAllByProcessCategory(processCategoryService.findById(id));
    }
}
