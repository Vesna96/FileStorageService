package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ThunderStoreGetUsersInOrgResponseDTO {
    private List<ThunderStoreGetUsersInOrgDTO> listOfUserFolders;
}
