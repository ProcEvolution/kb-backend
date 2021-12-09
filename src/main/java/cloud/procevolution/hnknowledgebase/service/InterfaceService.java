package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Interface;
import cloud.procevolution.hnknowledgebase.repository.InterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InterfaceService {

    private final InterfaceRepository interfaceRepository;

    public Interface save(Interface anInterface) {
        return this.interfaceRepository.save(anInterface);
    }

    public List<Interface> findAll() {
        return this.interfaceRepository.findAll();
    }

    public Interface findById(String id) {
        return this.interfaceRepository.findById(id).orElseThrow();
    }

    public void deleteAll(List<String> ids) {
        ids.forEach(this.interfaceRepository::deleteById);
    }
}
