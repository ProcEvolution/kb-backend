package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.ProcessCategory;
import cloud.procevolution.hnknowledgebase.repository.ProcessCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessCategoryService {

    private final ProcessCategoryRepository processCategoryRepository;


    public ProcessCategory findById(String id) {
        return this.processCategoryRepository.findById(id).orElseThrow();
    }

    public List<ProcessCategory> findAll() {
        return this.processCategoryRepository.findAll();
    }

    public ProcessCategory save(ProcessCategory processCategory) {
        return this.processCategoryRepository.save(processCategory);
    }

    public void deleteAllById(List<String> ids) {
        processCategoryRepository.deleteAll(
                ids.stream().map(s -> new ProcessCategory(s, null)).toList()
        );
    }
}
