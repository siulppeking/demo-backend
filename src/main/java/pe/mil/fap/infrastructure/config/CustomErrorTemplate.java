package pe.mil.fap.infrastructure.config;

import java.time.LocalDateTime;

public record CustomErrorTemplate(
        LocalDateTime datetime,
        String message,
        String details
) {
}

