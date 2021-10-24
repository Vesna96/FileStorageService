package FileStorage.service1.exception;

import lombok.Builder;
import lombok.Getter;

@Getter

@Builder
public class ActionNotAllowedException extends RuntimeException{
    private String message;
}
