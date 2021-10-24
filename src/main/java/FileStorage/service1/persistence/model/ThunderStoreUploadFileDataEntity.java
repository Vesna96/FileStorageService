package FileStorage.service1.persistence.model;

import lombok.*;

@Getter
@Builder
public class ThunderStoreUploadFileDataEntity {

    private String filename;
    private String path;
    private Long size;
}
