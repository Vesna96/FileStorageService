package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreCreateFolderDTO {
    private String foldername;
    private String type;
    private int size;
}
