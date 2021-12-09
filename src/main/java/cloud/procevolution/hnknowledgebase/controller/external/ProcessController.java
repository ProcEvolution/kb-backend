package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;


    @GetMapping(path = "/public/process")
    public List<Process> show() {
        var result = processService.findAll();
        return result;
    }

    @GetMapping(path = "/public/process/{id}")
    public Process findById(@PathVariable String id) {
        return this.processService.findById(id);
    }

}
