package pe.mil.fap.shared.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ResponseApi<T> success(T data, String message) {
        return new ResponseApi<>("success", message, data);
    }

    public static <T> ResponseApi<T> success(T data) {
        return new ResponseApi<>("success", "Operación exitosa", data);
    }

    public static <T> ResponseApi<T> warning(T data, String message) {
        return new ResponseApi<>("warning", message, data);
    }

    public static <T> ResponseApi<T> info(T data, String message) {
        return new ResponseApi<>("info", message, data);
    }

    public static <T> ResponseApi<T> danger(T data, String message) {
        return new ResponseApi<>("danger", message, data);
    }

    public static <T> ResponseApi<T> danger(String message) {
        return new ResponseApi<>("danger", message, null);
    }
}
