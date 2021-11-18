package FileStorage.service1.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@Builder
public class ThunderStoreDownloadFileData {
    private Resource resource;
}
