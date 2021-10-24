package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreUploadFilesDTO {

    private String filename;
    private String path;
    private Long size;

}