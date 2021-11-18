package FileStorage.service1.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileNotFoundException extends RuntimeException {
    private String message;
}

