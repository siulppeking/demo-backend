package pe.mil.fap.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.mil.fap.shared.responses.ResponseApi;
import pe.mil.fap.shared.responses.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseApi<List<ErrorResponse>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.add(new ErrorResponse(error.getField(), error.getDefaultMessage()))
        );
        return ResponseEntity.badRequest()
                .body(ResponseApi.danger(errors, "Errores de validación"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseApi<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(ResponseApi.danger(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseApi<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseApi.danger("Error interno del servidor: " + ex.getMessage()));
    }
}
