package br.com.fusion.banck.shared.handler;

import br.com.fusion.banck.shared.enums.StatusMessage;
import br.com.fusion.banck.shared.exceptions.FusionApiUserIsSave;
import br.com.fusion.banck.shared.exceptions.NotFoundHours;
import br.com.fusion.banck.shared.exceptions.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class HandlerFusion extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FusionApiUserIsSave.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseError> handleFusionApiUserIsSave(FusionApiUserIsSave ex) {
        ResponseError response = ResponseError.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleException(Exception ex) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NotFoundHours.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleNotFoundHours(NotFoundHours ex) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.PENDING.getDescription())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.DANIED.getDescription())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .message("Certifique-se que preencheu os campos corretamente.") // "Content-Type 'null' is not supported"
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }
}
