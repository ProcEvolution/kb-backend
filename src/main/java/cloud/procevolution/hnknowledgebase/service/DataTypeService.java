package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.DataType;
import cloud.procevolution.hnknowledgebase.repository.DataTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DataTypeService {

    private final DataTypeRepository dataTypeRepository;

    public DataType save(DataType dataType) {
        return this.dataTypeRepository.save(dataType);
    }

    public List<DataType> findAll() {
        return this.dataTypeRepository.findAll();
    }

    public DataType findById(String id) {
        return dataTypeRepository.findById(id).orElseThrow();
    }

    public void deleteAllById(List<String> ids) {
        ids.forEach(this.dataTypeRepository::deleteById);
    }
}
