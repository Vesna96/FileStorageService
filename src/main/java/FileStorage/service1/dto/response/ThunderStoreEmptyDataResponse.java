package FileStorage.service1.dto.response.responsedto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThunderStoreEmptyDataResponse {

    private int status;
    private String message;
    private String error;
}
