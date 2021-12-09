package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.Activity;
import cloud.procevolution.hnknowledgebase.entity.DataType;
import cloud.procevolution.hnknowledgebase.service.DataTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DataTypeController {

    private final DataTypeService dataTypeService;

    @GetMapping("/public/datatype")
    public List<DataType> findAll() {
        return this.dataTypeService.findAll();
    }

    @GetMapping("/public/datatype/{id}")
    public DataType findById(@PathVariable String id) {
        return this.dataTypeService.findById(id);
    }
}
