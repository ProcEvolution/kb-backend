package cloud.procevolution.hnknowledgebase.service;

import cloud.procevolution.hnknowledgebase.controller.external.WearableCategoryController;
import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import cloud.procevolution.hnknowledgebase.repository.WearableCategoryRepository;
import cloud.procevolution.hnknowledgebase.repository.WearableRepository;
import cloud.procevolution.hnknowledgebase.view.dto.WearableCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WearableService {
    @Value(value = "${data.dir.images}")
    private String imageDir;

    private final HttpServletRequest httpServletRequest;
    private final WearableRepository wearableRepository;
    private final WearableCategoryService wearableCategoryService;
    private final WearableCategoryRepository wearableCategoryRepository;

    public Wearable save(Wearable wearable) {
        if (wearable.getId() != null) {
            var wearableUpdate = this.wearableRepository.findById(wearable.getId()).get();
            wearable.setIconURL(wearableUpdate.getIconURL());
        }
        return this.wearableRepository.save(wearable);
    }

    public List<Wearable> findAll() {
        return this.wearableRepository.findAll();
    }

    public Wearable uploadFileById(String id, MultipartFile multipartFile) {
        String fileName = Timestamp.valueOf(LocalDateTime.now()).getTime() + "-" + Objects.requireNonNull(multipartFile.getOriginalFilename());
        try {
            try (OutputStream os = Files.newOutputStream(Path.of(imageDir, fileName))) {
                os.write(multipartFile.getBytes());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Wearable wearable = this.wearableRepository.findById(id)
                .orElseThrow();
        String hostPath = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
        wearable.setIconURL(hostPath + "/public/wearable/icon/" + fileName);
        return this.wearableRepository.save(wearable);
    }

    public ByteArrayResource downloadIconByName(String name) throws IOException {
        File file = Path.of(imageDir, name).toFile();
        if (file.exists()) {
            return new ByteArrayResource(new FileInputStream(file).readAllBytes());
        } else {
            throw new RuntimeException("image not exist");
        }
    }

    public void deleteAllById(List<String> ids) {
        ids.forEach(this.wearableRepository::deleteById);
    }
}
