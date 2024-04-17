package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/* import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList; */


public class InvertedIndexTest {

    private static WebPage page1;
    private static WebPage page2;
    private static Generator generator0;
    private static Generator generator1;
    private static Generator generator2;

    private static class MockGenerator implements Generator {

        private List<WebPage> webPageList;

        public MockGenerator(List<WebPage> webPageList) {
            this.webPageList = webPageList;
        }

        public List<WebPage> getPages() {
            return this.webPageList;
        }

        public int getNumberOfWebPages() {
            return this.webPageList.size();
        }   
    }

    @BeforeAll
    public static void setup() {

        Map<String, Integer> page1Content = new HashMap<>();
        page1Content.put("word1", 1);
        page1Content.put("word2", 1);
        page1 = new WebPage("http://page1.com", "title1", page1Content, 2);

        Map<String, Integer> page2Content = new HashMap<>();
        page2Content.put("word1", 1);
        page2Content.put("word3", 1);
        page2 = new WebPage("http://page2.com", "title2", page2Content, 2);

        generator0 = new MockGenerator(Arrays.asList());
        generator1 = new MockGenerator(Arrays.asList(page1));
        generator2 = new MockGenerator(Arrays.asList(page1, page2));
    
    }

    // Zero - page tests

    @Test
    public void getWebPagesContainingTerm_fromZeroPage_hasNoPage() {
        Index index = new InvertedIndex(generator0);
        assertTrue(index.getWebPagesContainingTerm("word1").isEmpty());
    }

    @Test
    public void getNumberOfWebPagesContainingTerm_fromZeroPage_hasCorrectNumber() {
        Index index = new InvertedIndex(generator0);
        assertEquals(0, index.getNumberOfWebPagesContainingTerm("word1"));
    }


    @Test
    public void getNumberOfWebPages_fromZeroPage_hasCorrectNumber() {
        Index index = new InvertedIndex(generator0);
        assertEquals(0, index.getNumberOfWebPages());
    }

    // One - page tests
    
    @Test
    public void getWebPagesContainingTerm_fromOnePage_withWord1_hasCorrectPageSet() {
        Index index = new InvertedIndex(generator1);
        assertEquals(new HashSet<>(Arrays.asList(page1)), index.getWebPagesContainingTerm("word1"));
    }


    @Test
    public void getWebPagesContainingTerm_fromOnePage_withWrongWord_hasEmptyPageSet() {
        Index index = new InvertedIndex(generator1);
        assertEquals(new HashSet<>(), index.getWebPagesContainingTerm("WRONG_WORD"));
    }


    @Test
    public void getNumberOfWebPagesContainingTerm_fromOnePage_withWord1_hasCorrectNumber() {
        Index index = new InvertedIndex(generator1);
        assertEquals(1, index.getNumberOfWebPagesContainingTerm("word1"));
    }


    @Test
    public void getNumberOfWebPagesContainingTerm_fromOnePage_withWrongWord_hasCorrectNumber() {
        Index index = new InvertedIndex(generator1);
        assertEquals(0, index.getNumberOfWebPagesContainingTerm("WRONG_WORD"));
    }


    @Test
    public void getNumberOfWebPages_fromOnePage_hasCorrectNumber() {
        Index index = new InvertedIndex(generator1);
        assertEquals(1, index.getNumberOfWebPages());
    }

    // Two - page tests
  
    @Test
    public void getWebPagesContainingTerm_fromTwoPages_withWord1_hasCorrectPages() {
        Index index = new InvertedIndex(generator2);
        assertEquals(new HashSet<WebPage>(Arrays.asList(page1, page2)), index.getWebPagesContainingTerm("word1"));
    }


    @Test
    public void getWebPagesContainingTerm_fromTwoPages_withWord3_hasCorrectPages() {
        Index index = new InvertedIndex(generator2);
        assertEquals(new HashSet<WebPage>(Arrays.asList(page2)), index.getWebPagesContainingTerm("word3"));
    }


    @Test
    public void getWebPagesContainingTerm_fromTwoPages_withWrongWord_hasNoPages() {
        Index index = new InvertedIndex(generator2);
        assertEquals(new HashSet<>(), index.getWebPagesContainingTerm("WRONG_WORD"));
    }


    @Test
    public void getNumberOfWebPagesContainingTerm_fromTwoPages_withWord1_hasCorrectNumber() {
        Index index = new InvertedIndex(generator2);
        assertEquals(2, index.getNumberOfWebPagesContainingTerm("word1"));
    }

    
    @Test
    public void getNumberOfWebPagesContainingTerm_fromTwoPages_withWord3_hasCorrectNumber() {
        Index index = new InvertedIndex(generator2);
        assertEquals(1, index.getNumberOfWebPagesContainingTerm("word3"));
    }


    @Test
    public void getNumberOfWebPagesContainingTerm_fromTwoPages_withWrongWord_hasCorrectNumber() {
        Index index = new InvertedIndex(generator2);
        assertEquals(0, index.getNumberOfWebPagesContainingTerm("WRONG_WORD"));
    }


    @Test
    public void getNumberOfWebPages_fromTwoPages_hasCorrectNumber() {
        Index index = new InvertedIndex(generator2);
        assertEquals(2, index.getNumberOfWebPages());
    }
    
}
