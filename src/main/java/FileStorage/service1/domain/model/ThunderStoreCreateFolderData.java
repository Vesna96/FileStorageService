package FileStorage.service1.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreCreateFolderData {
    private String foldername;
    private String type;
    private int size;
}
