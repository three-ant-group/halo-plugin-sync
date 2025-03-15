package run.halo.starter.model;

import lombok.Data;

@Data
public class SyncResult<T> {
    private int code;
    private String message;
    private T data;

    public static <T> SyncResult<T> success(T data) {
        SyncResult<T> result = new SyncResult<>();
        result.code = 0;
        result.message = "Success";
        result.data = data;
        return result;
    }

    public static SyncResult<String> failure(String message) {
        SyncResult<String> result = new SyncResult<>();
        result.code = 500;
        result.message = message;
        return result;
    }
}