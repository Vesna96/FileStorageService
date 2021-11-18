package FileStorage.service1.controller.exception.handler;

import FileStorage.service1.dto.response.responsedto.ThunderStoreEmptyDataResponse;
import FileStorage.service1.exception.ActionNotAllowedException;
import FileStorage.service1.exception.DirectoryAlreadyExistsException;
import FileStorage.service1.exception.DirectoryNotFoundException;
import FileStorage.service1.exception.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DirectoryAlreadyExistsException.class)
    public ResponseEntity<ThunderStoreEmptyDataResponse> handleDirectoryAlreadyExistsException(DirectoryAlreadyExistsException e) {
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ThunderStoreEmptyDataResponse> handleFileNotFoundException(FileNotFoundException e) {
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<ThunderStoreEmptyDataResponse> handleActionNotAllowedException(ActionNotAllowedException e) {
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DirectoryNotFoundException.class)
    public ResponseEntity<ThunderStoreEmptyDataResponse> handleDirectoryNotFoundException(DirectoryNotFoundException e) {
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build(), HttpStatus.NOT_FOUND);
    }
}