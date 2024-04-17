package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {

    WebServer server = null;

    private static class QueryMocker implements QueryEngine {

        @Override
        public List<WebPage> query(String term) {
            if (term.isBlank()) {
                return new ArrayList<>();
            }
            else {
                Map<String, Integer> map = new HashMap<>();
                map.put("word1", 1);
                return List.of(new WebPage("http://page1.com", "title1", map, 1));
            }
            
        } 
    }

    private String httpGet(String url) {
        URI uri = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } 
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    @BeforeAll
    void setUp() {
        try {
            Random rnd = new Random();
            while (server == null) {
                try {
                    server = new WebServer(rnd.nextInt(60000) + 1024, new QueryMocker());
                    server.startServer();
                }
                catch (BindException e) {
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @Test
    public void getHomePage_serverResponds() {
        assertTrue(httpGet(String.format("http://localhost:%d/", server.getPort())).length() != 0);
    }


    @Test
    public void getFavicon_serverResponds() {
        assertTrue(httpGet(String.format("http://localhost:%d/favicon.ico", server.getPort())).length() != 0);
    }


    @Test
    public void getCodeJs_serverResponds() {
        assertTrue(httpGet(String.format("http://localhost:%d/code.js", server.getPort())).length() != 0);
    }


    @Test
    public void getStyleCss_serverResponds() {
        assertTrue(httpGet(String.format("http://localhost:%d/style.css", server.getPort())).length() != 0);
    }


    @Test
    public void getEmptyQuery_serverSendEmptyList() {
        assertEquals("[]", httpGet(String.format("http://localhost:%d/search?q=", server.getPort())));
    }


    @Test
    public void getNonEmptyQuery_serverSendsWebPageList() {
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]", httpGet(String.format("http://localhost:%d/search?q=word1", server.getPort())));
    }


    @AfterAll
    void tearDown() {
        server.stopServer(0);
        server = null;
    }
}
