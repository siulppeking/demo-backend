package pe.mil.fap.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_m_categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategoria")
    private Long idCategoria;
    private String sigla;
    @Column(name = "nomcat")
    private String nombre;
    private String estado;
    @Column(name = "usucre")
    private Long usuarioCreacion;
    @Column(name = "feccre")
    private LocalDateTime fechaCreacion;
    @Column(name = "usumod")
    private Long usuarioModificacion;
    @Column(name = "fecmod")
    private LocalDateTime fechaModificacion;

}
