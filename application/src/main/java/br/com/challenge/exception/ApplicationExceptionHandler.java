package br.com.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Classe handler global de exception
 * para tratar status e mensagem do retorno de servicos
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BaseServiceException.class)
    public ResponseEntity<ApiErro> handleBaseServiceException(BaseServiceException exception, WebRequest request) {
        ApiErro apiErro = ApiErro.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity(apiErro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErro> handleEntityNotFoundException(WebRequest request) {
        ApiErro apiErro = ApiErro.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Recurso nao encontrado")
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity(apiErro, HttpStatus.NOT_FOUND);
    }

}
