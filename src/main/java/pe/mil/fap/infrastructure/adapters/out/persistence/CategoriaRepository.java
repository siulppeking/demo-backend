package pe.mil.fap.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mil.fap.domain.entities.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca una categoría por su nombre
     * @param nombre el nombre de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Categoria> findByNombre(String nombre);

    /**
     * Busca una categoría por su sigla
     * @param sigla la sigla de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Categoria> findBySigla(String sigla);

    /**
     * Busca todas las categorías activas
     * @return Lista de categorías con estado 'A'
     */
    @Query("SELECT c FROM Categoria c WHERE c.estado = '1'")
    List<Categoria> findAllActive();

    /**
     * Busca categorías por estado
     * @param estado el estado a buscar
     * @return Lista de categorías con el estado indicado
     */
    List<Categoria> findByEstado(String estado);

    /**
     * Verifica si existe una categoría con el nombre dado (Oracle compatible)
     * @param nombre el nombre a verificar
     * @return true si existe, false en caso contrario
     */
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM t_m_categoria WHERE nomcat = :nombre", 
           nativeQuery = true)
    int existsByNombreQuery(@Param("nombre") String nombre);

    /**
     * Verifica si existe una categoría con la sigla dada (Oracle compatible)
     * @param sigla la sigla a verificar
     * @return true si existe, false en caso contrario
     */
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM t_m_categoria WHERE sigla = :sigla", 
           nativeQuery = true)
    int existsBySiglaQuery(@Param("sigla") String sigla);

    /**
     * Obtiene todas las categorías ordenadas descendentemente por ID con paginación
     * Compatible con Oracle Database
     */
    @Query(value = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY idcategoria DESC) as rn, c.* " +
           "FROM t_m_categoria c) WHERE rn > :offset AND rn <= :limit",
           nativeQuery = true)
    List<Categoria> findAllOrderByIdDescPaginated(@Param("offset") long offset, @Param("limit") long limit);

    /**
     * Cuenta total de categorías
     */
    @Query(value = "SELECT COUNT(*) FROM t_m_categoria", nativeQuery = true)
    long countAll();
}
