package app;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class FrontEndTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void healthRespondsOk() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = HttpRequest.GET("/").accept(MediaType.TEXT_HTML);
        String html = client.retrieve(request);
        assertNotNull(html);
        assertTrue(html.contains("Svelte app"));
    }
}
