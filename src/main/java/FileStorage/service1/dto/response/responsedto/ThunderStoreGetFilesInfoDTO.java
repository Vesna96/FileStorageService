package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreGetFilesInfoDTO {
    private String filename;
    private String extension;
    private int size;
}
