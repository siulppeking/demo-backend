package pe.mil.fap.infrastructure.adapters.in.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para actualizar una categoría")
public class CategoriaUpdateDTO {
    
    @NotNull(message = "El ID de categoría es requerido")
    @Schema(description = "ID de la categoría", example = "443")
    private Long idCategoria;
    
    @NotBlank(message = "La sigla es requerida")
    @Size(min = 1, max = 10, message = "La sigla debe tener entre 1 y 10 caracteres")
    @Schema(description = "Sigla de la categoría", example = "COM", minLength = 1, maxLength = 10)
    private String sigla;
    
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de la categoría", example = "Comidas al Paso", minLength = 3, maxLength = 50)
    private String nombre;
}

