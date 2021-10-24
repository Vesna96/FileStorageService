package FileStorage.service1.persistence.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreCreateFolderEntity
{
    private String foldername;
    private String type;
    private int size;
}
