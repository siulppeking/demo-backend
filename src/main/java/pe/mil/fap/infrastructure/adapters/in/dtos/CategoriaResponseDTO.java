package pe.mil.fap.infrastructure.adapters.in.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para una categoría")
public class CategoriaResponseDTO {
    
    @Schema(description = "ID de la categoría", example = "443")
    private Long idCategoria;
    
    @Schema(description = "Sigla de la categoría", example = "COM")
    private String sigla;
    
    @Schema(description = "Nombre de la categoría", example = "Comidas al Paso")
    private String nombre;
}

