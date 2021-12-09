package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import cloud.procevolution.hnknowledgebase.repository.WearableCategoryRepository;
import cloud.procevolution.hnknowledgebase.repository.WearableRepository;
import cloud.procevolution.hnknowledgebase.view.dto.WearableCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WearableCategoryService {

    @Value(value = "${data.dir.images}")
    private String imageDir;

    private final WearableCategoryRepository wearableCategoryRepository;
    private final HttpServletRequest httpServletRequest;
    private final WearableRepository wearableRepository;

    public WearableCategory save(WearableCategory wearableCategory) {
        return this.wearableCategoryRepository.save(wearableCategory);
    }

    public List<WearableCategoryDto> findAll() {
        List<WearableCategory> categories = this.wearableCategoryRepository.findAll();
        List<WearableCategoryDto> result = new ArrayList<>(categories.size());
        for (WearableCategory wearableCategory : categories) {
            List<Wearable> wearables;
            try {
                wearables = wearableRepository.findByWearableCategory(wearableCategory);
            } catch (MappingException e) {
                wearables = new ArrayList<>();
            }
            result.add(WearableCategoryDto.from(wearableCategory, wearables));
        }
        return result;
    }

    public WearableCategoryDto findById(String id) {
        WearableCategory wearableCategory = this.wearableCategoryRepository.findById(id).orElseGet(WearableCategory::new);
        List<Wearable> wearables = wearableRepository.findByWearableCategory(wearableCategory);
        return WearableCategoryDto.from(wearableCategory, wearables);
    }

    public WearableCategory uploadFileById(String id, MultipartFile multipartFile) {
        String fileName = Timestamp.valueOf(LocalDateTime.now()).getTime() + "-" + Objects.requireNonNull(multipartFile.getOriginalFilename());
        try {
            try (OutputStream os = Files.newOutputStream(Path.of(imageDir, fileName))) {
                os.write(multipartFile.getBytes());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        WearableCategory wearableCategory = this.wearableCategoryRepository.findById(id)
                .orElseThrow();
        String hostPath = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
        wearableCategory.setIconURL(hostPath + "/public/wearablecategory/icon/" + fileName);
        return this.wearableCategoryRepository.save(wearableCategory);
    }

    public ByteArrayResource downloadIconByName(String name) throws IOException {
        File file = Path.of(imageDir, name).toFile();
        if (file.exists()) {
            return new ByteArrayResource(new FileInputStream(file).readAllBytes());
        } else {
            throw new RuntimeException("image not exist");
        }

    }

    public void bulkDelete(List<String> ids) throws RuntimeException {
        try {
            this.wearableCategoryRepository
                    .deleteAll(
                            ids.stream()
                                    .map(s -> new WearableCategory(s, null, null, null))
                                    .toList()
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public WearableCategory update(String id, WearableCategory wearableCategory) {
        var wearableCategoryUpdate = this.wearableCategoryRepository.findById(id)
                .orElseThrow();
        wearableCategoryUpdate.setDescription(wearableCategory.getDescription());
        wearableCategoryUpdate.setName(wearableCategory.getName());

        return null;
    }
}
