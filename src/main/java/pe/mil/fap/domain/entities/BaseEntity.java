package pe.mil.fap.domain.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class BaseEntity {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
