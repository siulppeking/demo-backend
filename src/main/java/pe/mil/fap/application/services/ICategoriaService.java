package pe.mil.fap.application.services;

import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaRequestDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaUpdateDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoriaService {

    /**
     * Obtiene todas las categorías paginadas, ordenadas descendentemente por ID
     * @param pageable información de paginación
     * @return página de categorías
     */
    Page<CategoriaResponseDTO> obtenerTodas(Pageable pageable);

    /**
     * Obtiene una categoría por su ID
     * @param id el ID de la categoría
     * @return DTO con la categoría encontrada
     */
    CategoriaResponseDTO obtenerPorId(Long id);

    /**
     * Crea una nueva categoría
     * @param requestDTO los datos de la categoría a crear (sigla y nombre)
     * @return DTO de la categoría creada
     */
    CategoriaResponseDTO crear(CategoriaRequestDTO requestDTO);

    /**
     * Actualiza una categoría existente
     * @param updateDTO los datos actualizados (idCategoria, sigla y nombre)
     * @return DTO de la categoría actualizada
     */
    CategoriaResponseDTO actualizar(CategoriaUpdateDTO updateDTO);

    /**
     * Elimina una categoría por su ID
     * @param id el ID de la categoría a eliminar
     */
    void eliminar(Long id);
}
