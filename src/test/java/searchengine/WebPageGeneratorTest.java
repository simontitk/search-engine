package searchengine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
/* import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach; */


public class WebPageGeneratorTest {

    @Test
    public void constructor_nonexistentFile_throwsException() {
        assertThrows(FileNotFoundException.class, ()-> {
            new WebPageGenerator("data/NONEXISTIENT_FILE.txt", "*PAGE:");});
    }


    @Test
    public void constructor_existingTestFile_throwsNoException() {
        assertDoesNotThrow(()-> {new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");});
    }


    @Test
    public void generate_fromTestFileOnePage_hasOneWebPage() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");
        assertEquals(1, generator.getNumberOfWebPages());
    }


    @Test
    public void generate_fromTestFileOnePage_hasCorrectUrl() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");
        assertEquals("http://page1.com", generator.getPages().get(0).getUrl());
    }
    

    @Test
    public void generate_fromTestFileOnePage_hasCorectSiteName() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");
        assertEquals("title1", generator.getPages().get(0).getSiteName());
    }


    @Test
    public void generate_fromTestFileOnePage_hasCorrectTermset() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");
        assertEquals(new HashSet<>(Arrays.asList("title1", "word1", "word2")), generator.getPages().get(0).getTermSet());
    }


    @Test
    public void generate_fromTestFileOnePage_hasCorrectPageLength() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-one-page.txt", "*PAGE:");
        assertEquals(3, generator.getPages().get(0).getPageLength());

    }


    @Test
    public void generate_fromTestFileTwoPages_hasTwoWebPages() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-two-pages.txt", "*PAGE:");
        assertEquals(2, generator.getNumberOfWebPages());
    }


    @Test
    public void generate_fromTestFileTwoPages_hasCorrectUrls() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-two-pages.txt", "*PAGE:");
        assertEquals("http://page1.com", generator.getPages().get(0).getUrl());
        assertEquals("http://page2.com", generator.getPages().get(1).getUrl());

    }


    @Test
    public void generate_fromTestFileTwoPages_hasCorectSiteNames() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-two-pages.txt", "*PAGE:");
        assertEquals("title1", generator.getPages().get(0).getSiteName());
        assertEquals("title2", generator.getPages().get(1).getSiteName());

    }

    
    @Test
    public void generate_fromTestFileTwoPages_hasCorrectTermsets() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-two-pages.txt", "*PAGE:");
        assertEquals(new HashSet<>(Arrays.asList("title1", "word1", "word2")), generator.getPages().get(0).getTermSet());
        assertEquals(new HashSet<>(Arrays.asList("title2", "word1", "word3")), generator.getPages().get(1).getTermSet());

    }


    @Test
    public void generate_fromTestFileTwoPages_hasCorrectPageLengths() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-two-pages.txt", "*PAGE:");
        assertEquals(3, generator.getPages().get(0).getPageLength());
        assertEquals(3, generator.getPages().get(1).getPageLength());
    }

    @Test
    public void generate_fromErrorFile_hasCorectSiteNames() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-errors.txt", "*PAGE:");
        assertEquals("title1", generator.getPages().get(0).getSiteName());
        assertEquals("title2", generator.getPages().get(1).getSiteName());
    }

    @Test
    public void generate_fromEmptyFile_throwsExcpetion() throws FileNotFoundException, IOException {
        assertThrows(NullPointerException.class, () -> {
            new WebPageGenerator("data/test-file-empty.txt", "*PAGE:");
        });
    }

    @Test
    public void generate_fromOnlyPagemarkerFile_hasNoWebPages() throws FileNotFoundException, IOException {
        Generator generator = new WebPageGenerator("data/test-file-pagemarkers.txt", "*PAGE:");
        assertEquals(0, generator.getNumberOfWebPages());
    }
 
}
