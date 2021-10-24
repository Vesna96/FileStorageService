package FileStorage.service1.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileLoadFailedException extends RuntimeException{
    private String message;
}
