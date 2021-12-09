package cloud.procevolution.hnknowledgebase.view.dto;

import cloud.procevolution.hnknowledgebase.entity.Wearable;
import cloud.procevolution.hnknowledgebase.entity.WearableCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WearableCategoryDto {

    private String id;

    private String name;

    private String description;

    private String iconURL;

    private List<Wearable> wearables;

    public static WearableCategoryDto from(WearableCategory wearableCategory, List<Wearable> wearables) {
        return WearableCategoryDto.builder()
                .id(wearableCategory.getId())
                .description(wearableCategory.getDescription())
                .iconURL(wearableCategory.getIconURL())
                .name(wearableCategory.getName())
                .wearables(wearables)
                .build();
    }

    public static WearableCategory from(WearableCategoryDto wearableCategoryDto) {
        WearableCategory wearableCategory = new WearableCategory();
        wearableCategory.setId(wearableCategoryDto.getId());
        wearableCategory.setName(wearableCategoryDto.getName());
        wearableCategory.setIconURL(wearableCategoryDto.getIconURL());
        wearableCategory.setDescription(wearableCategoryDto.getDescription());
        return wearableCategory;
    }
}
