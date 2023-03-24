package app;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HealthTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void healthRespondsOk() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = HttpRequest.GET("/health");
        HttpResponse<Map> response = client.exchange(request, Map.class);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(Collections.singletonMap("status", "UP"), response.body());
    }
}
