package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

import org.springframework.core.io.Resource;

@Getter
@Builder
public class ThunderStoreDownloadFileDTO
{
    private Resource resource;
}
