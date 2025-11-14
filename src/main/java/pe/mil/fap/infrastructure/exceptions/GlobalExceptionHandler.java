package pe.mil.fap.infrastructure.exceptions;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.mil.fap.shared.responses.ResponseApi;
import pe.mil.fap.shared.responses.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "pe.mil.fap.infrastructure.adapters.in.controllers")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "400",
            description = "Errores de validación en la solicitud",
            content = @Content(schema = @Schema(implementation = ResponseApi.class))
    )
    public ResponseEntity<ResponseApi<List<ErrorResponse>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        
        return ResponseEntity.badRequest()
                .body(ResponseApi.danger(errors, "Errores de validación en la solicitud"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "400",
            description = "Error de lógica de negocio",
            content = @Content(schema = @Schema(implementation = ResponseApi.class))
    )
    public ResponseEntity<ResponseApi<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(ResponseApi.danger(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ResponseApi.class))
    )
    public ResponseEntity<ResponseApi<Void>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseApi.danger("Error interno del servidor: " + ex.getMessage()));
    }
}
