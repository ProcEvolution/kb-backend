package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Process;
import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import cloud.procevolution.hnknowledgebase.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    public List<Process> findAll() {

        var result = this.processRepository.findAll();
        return result;
    }

    public Process findById(String id) {
        return this.processRepository.findById(id).orElseThrow();
    }

    public Process save(Process process) {
        return this.processRepository.save(process);
    }

    public List<Process> findAllByProcessCategory(ProcessCategory processCategory) {
        return this.processRepository.findAllByProcessCategory(processCategory);
    }

    public void deleteAllById(List<String> ids) {
        processRepository.deleteAll(
                ids.stream().map(s -> new Process(s, null, null, null)).toList()
        );

    }
}
