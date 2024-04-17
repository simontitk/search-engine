package searchengine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.Test;


public class WebPageTest {

    @Test
    public void constructor_emptyTermCountTable_throwsException() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new WebPage("http://test.com", "test" , new HashMap<>() , 0);});
    }
  

    @Test
    public void constructor_oneTermCountTable_throwsNoException() {
        assertDoesNotThrow( ()-> {
            Map<String, Integer> map = new HashMap<>();
            map.put("word1", 1);
            new WebPage("http://test.com", "test", map, 1);});
    }


    @Test
    public void constructor_oneTitleWord_containsTitleWord() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com", "titleword" , oneTermMap , 1);
        assertEquals(1, page.getTermCount("titleword"));
    }


    @Test
    public void constructor_threeTitleWords_containsAllTitleWords() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com", "titleword titleword titleword" , oneTermMap , 1);
        assertEquals(3, page.getTermCount("titleword"));
    }
    
    
    @Test
    public void constructor_capitalTitleWord_containsLowerCaseTitleWord() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com" , "TitLewOrd" , oneTermMap , 1);
        assertEquals(new HashSet<>(Arrays.asList("titleword", "word1")), page.getTermSet());
    }
    
    
    @Test
    public void constructor_titleWordWithSpaces_containsStrippedTitleWord() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com" , "          titleword      " , oneTermMap , 1);
        assertEquals(new HashSet<>(Arrays.asList("titleword", "word1")), page.getTermSet());
    }
    
    
    @Test
    public void constructor_threeTitleWords_pageLengthIncreased() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com" , "titleword titleword titleword" , oneTermMap , 1);
        assertEquals(4, page.getPageLength());
    }
    
    
    @Test
    public void getTermCount_wrongWord_containsNoKey() {
        Map<String, Integer> oneTermMap = new HashMap<>();
        oneTermMap.put("word1", 1);
        WebPage page = new WebPage("http://test.com" , "title" , oneTermMap , 1);
        assertEquals(0, page.getTermCount("WRONG_WORD"));
    }

}




