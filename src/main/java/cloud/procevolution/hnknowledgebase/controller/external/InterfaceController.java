package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.entity.Interface;
import cloud.procevolution.hnknowledgebase.service.InterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class InterfaceController {

    private final InterfaceService interfaceService;

    @GetMapping("/public/interface")
    public List<Interface> findAll() {
        return this.interfaceService.findAll();
    }

    @GetMapping("/public/interface/{id}")
    public Interface findById(@PathVariable String id) {
        return this.interfaceService.findById(id);
    }
}
