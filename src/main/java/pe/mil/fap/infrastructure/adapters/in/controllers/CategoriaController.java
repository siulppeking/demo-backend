package pe.mil.fap.infrastructure.adapters.in.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.mil.fap.application.services.ICategoriaService;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaRequestDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaUpdateDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaResponseDTO;
import pe.mil.fap.shared.responses.ResponseApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de categorías del sistema")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías paginadas y ordenadas descendentemente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas exitosamente", content = @Content(schema = @Schema(implementation = ResponseApi.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseApi<Page<CategoriaResponseDTO>>> obtenerTodas(
            @Parameter(description = "Parámetros de paginación (page, size, sort)") Pageable pageable) {
        return ResponseEntity.ok(ResponseApi.success(
                categoriaService.obtenerTodas(pageable),
                "Categorías obtenidas exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría específica por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseApi<CategoriaResponseDTO>> obtenerPorId(
            @Parameter(description = "ID de la categoría", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.ok(ResponseApi.success(
                    categoriaService.obtenerPorId(id),
                    "Categoría obtenida exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseApi.danger(e.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría con sigla y nombre únicos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría duplicada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseApi<CategoriaResponseDTO>> crear(
            @Valid @RequestBody CategoriaRequestDTO requestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseApi.success(
                            categoriaService.crear(requestDTO),
                            "Categoría creada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseApi.danger(e.getMessage()));
        }
    }

    @PutMapping
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseApi<CategoriaResponseDTO>> actualizar(
            @Valid @RequestBody CategoriaUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(ResponseApi.success(
                    categoriaService.actualizar(updateDTO),
                    "Categoría actualizada exitosamente"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseApi.danger(e.getMessage()));
            }
            return ResponseEntity.badRequest()
                    .body(ResponseApi.danger(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseApi<Void>> eliminar(
            @Parameter(description = "ID de la categoría a eliminar", required = true) @PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseApi.success(null, "Categoría eliminada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseApi.danger(e.getMessage()));
        }
    }
}
