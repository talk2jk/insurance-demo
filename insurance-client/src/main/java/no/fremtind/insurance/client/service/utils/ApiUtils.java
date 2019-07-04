package no.fremtind.insurance.client.service.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class ApiUtils {
    public static <T> HttpEntity<T> createRequestEntity(T body, MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);

        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        return entity;
    }
}
