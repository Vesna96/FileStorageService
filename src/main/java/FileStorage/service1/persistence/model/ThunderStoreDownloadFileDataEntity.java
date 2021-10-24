package FileStorage.service1.persistence.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@Builder
public class ThunderStoreDownloadFileDataEntity {
    private Resource resource;
}
