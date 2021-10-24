package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ThunderStoreGetFilesInfoResponseDTO {
    private List<ThunderStoreGetFilesInfoDTO> listOfFiles;
}
