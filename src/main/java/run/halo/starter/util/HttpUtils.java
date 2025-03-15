package run.halo.starter.util;

import cn.hutool.http.HttpException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class HttpUtils {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .version(HttpClient.Version.HTTP_2)
        .build();

    public static String get(String url, Map<String, String> params) throws HttpException {
        try {
            String fullUrl = buildUrlWithParams(url, params);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new HttpException("GET request failed", e);
        }
    }

    public static String postJson(String url, String json,String token,String session) throws HttpException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("cookie","XSRF-TOKEN="+token+";SESSION="+session)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new HttpException("POST JSON failed", e);
        }
    }

    // 检查 HTTP 状态码
    private static void checkStatus(HttpResponse<?> response) throws HttpException {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new HttpException("HTTP Error: " + response.statusCode(), response.statusCode());
        }
    }

    // 构建带参数的 URL
    private static String buildUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) return url;

        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")) sb.append('?');
        else if (!url.endsWith("&")) sb.append('&');

        params.forEach((key, value) -> {
            sb.append(encode(key))
                .append('=')
                .append(encode(value))
                .append('&');
        });
        sb.deleteCharAt(sb.length() - 1); // 删除末尾的 &
        return sb.toString();
    }

    // URL 编码
    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
