package pe.mil.fap.application.services;

import org.springframework.stereotype.Service;
import pe.mil.fap.domain.entities.Categoria;
import pe.mil.fap.infrastructure.adapters.out.persistence.CategoriaRepository;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaRequestDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaUpdateDTO;
import pe.mil.fap.infrastructure.adapters.in.dtos.CategoriaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<CategoriaResponseDTO> obtenerTodas(Pageable pageable) {
        // Calcular offset y limit compatible con Oracle
        long offset = (long) pageable.getPageNumber() * pageable.getPageSize();
        long limit = offset + pageable.getPageSize();
        
        // Obtener datos paginados
        List<Categoria> content = categoriaRepository.findAllOrderByIdDescPaginated(offset, limit);
        
        // Contar total de registros
        long total = categoriaRepository.countAll();
        
        // Convertir a DTOs
        List<CategoriaResponseDTO> dtos = content.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(dtos, pageable, total);
    }

    @Override
    public CategoriaResponseDTO obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::entityToResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO requestDTO) {
        if (categoriaRepository.existsByNombreQuery(requestDTO.getNombre()) > 0) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + requestDTO.getNombre());
        }
        if (categoriaRepository.existsBySiglaQuery(requestDTO.getSigla()) > 0) {
            throw new IllegalArgumentException("Ya existe una categoría con la sigla: " + requestDTO.getSigla());
        }
        
        Categoria categoria = new Categoria();
        categoria.setNombre(requestDTO.getNombre());
        categoria.setSigla(requestDTO.getSigla());
        categoria.setEstado("1");
        categoria.setFechaCreacion(LocalDateTime.now());
        
        Categoria saved = categoriaRepository.save(categoria);
        return entityToResponseDTO(saved);
    }

    @Override
    public CategoriaResponseDTO actualizar(CategoriaUpdateDTO updateDTO) {
        Categoria categoriaExistente = categoriaRepository.findById(updateDTO.getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + updateDTO.getIdCategoria()));
        
        if (!categoriaExistente.getNombre().equals(updateDTO.getNombre()) &&
            categoriaRepository.existsByNombreQuery(updateDTO.getNombre()) > 0) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + updateDTO.getNombre());
        }
        
        if (!categoriaExistente.getSigla().equals(updateDTO.getSigla()) &&
            categoriaRepository.existsBySiglaQuery(updateDTO.getSigla()) > 0) {
            throw new IllegalArgumentException("Ya existe una categoría con la sigla: " + updateDTO.getSigla());
        }
        
        categoriaExistente.setNombre(updateDTO.getNombre());
        categoriaExistente.setSigla(updateDTO.getSigla());
        categoriaExistente.setFechaModificacion(LocalDateTime.now());
        
        Categoria updated = categoriaRepository.save(categoriaExistente);
        return entityToResponseDTO(updated);
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Categoria a CategoriaResponseDTO
     */
    private CategoriaResponseDTO entityToResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getSigla(),
                categoria.getNombre()
        );
    }
}

