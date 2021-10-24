package FileStorage.service1.persistence.model;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThunderStoreGetFilesInfoEntity {
    public String filename;
    private String extension;
    public int size;
}
