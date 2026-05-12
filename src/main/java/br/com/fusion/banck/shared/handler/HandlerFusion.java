package br.com.fusion.banck.shared.handler;

import br.com.fusion.banck.shared.enums.StatusMessage;
import br.com.fusion.banck.shared.exceptions.FusionApiUserIsSave;
import br.com.fusion.banck.shared.exceptions.NotFoundHours;
import br.com.fusion.banck.shared.exceptions.NotSendQueue;
import br.com.fusion.banck.shared.exceptions.ResponseError;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Certifique-se que preencheu os campos corretamente.")
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseError> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Credenciais inválidas. Verifique seu email e senha.")
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message("Este método HTTP não é permitido para este endpoint. Verifique a documentação da API para os métodos suportados.")
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

    ResponseError response = ResponseError.builder()
            .statusMessage(StatusMessage.FAILED.getDescription())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .message("O corpo da requisição está malformado ou ausente. Verifique se o JSON está correto e se todos os campos obrigatórios estão presentes.")
            .timestamp(System.currentTimeMillis())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotSendQueue.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleNotSendQueue(NotSendQueue ex) {
        ResponseError response = ResponseError.builder()
                .statusMessage(StatusMessage.FAILED.getDescription())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
}
