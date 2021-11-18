package FileStorage.service1.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreGetFileInfoData {
    private String filename;
    private String extension;
    private int size;
}
