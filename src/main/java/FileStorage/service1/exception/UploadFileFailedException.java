package FileStorage.service1.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadFileFailedException extends RuntimeException{
    private String message;
}
