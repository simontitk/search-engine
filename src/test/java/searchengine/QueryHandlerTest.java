package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Questions for team:
 * 1. How should I get numberOfWebPages for the Mock index
 * 2. none of my tests run, and its unclear what the issue is
 */

public class QueryHandlerTest {
    
    public static Set<WebPage> setWordUSA, setWordPresident, setWordDK, setWordUK, setEmpty;

    public static WebPage page1, page2, page3;
    
    public static HashMap<String, Set<WebPage>> mapOfTermsToPages0;
    public static HashMap<String, Set<WebPage>> mapOfTermsToPages1;
    public static HashMap<String, Set<WebPage>> mapOfTermsToPages2;

    public static Index index0;
    public static Index index1;
    public static Index index2;

    public static Ranker ranker = new Ranker();

    private static class MockIndex implements Index {

        private Map<String, Set<WebPage>> index;
        private int numberOfWebPages = 3;

        public MockIndex (Map<String, Set<WebPage>> mapOfTermsToPages) {
            this.index = mapOfTermsToPages;
        }


        public Set<WebPage> getWebPagesContainingTerm(String term) {
            Set<WebPage> result = this.index.get(term);
            if (result == null) {
                return new HashSet<>();
            }
            return result;
        }


        public int getNumberOfWebPagesContainingTerm(String term) {
            Set<WebPage> webPages = getWebPagesContainingTerm(term);
            if (webPages == null) {
                return 0;
            }
            return webPages.size();
        }

    
        public int getNumberOfWebPages() {
            return this.numberOfWebPages;
        }

    }

    @BeforeAll
    public static void setup() {

        // Map<String, Integer> page1Content = new HashMap<>();
        // WebPage page1 = new WebPage("http://page1.com", "title1", page1Content, 2);

        Map<String, Integer> page1Content = new HashMap<>();
        page1Content.put("usa", 3);
        page1Content.put("president", 3);
        page1 = new WebPage("http://page1.com", "title1", page1Content, 6);

        Map<String, Integer> page2Content = new HashMap<>();
        page2Content.put("usa", 2);
        page2Content.put("president", 2);
        page2Content.put("DK", 2);
        page2 = new WebPage("http://page2.com", "title2", page2Content, 6);

        Map<String, Integer> page3Content = new HashMap<>();
        page3Content.put("free", 1);
        page3Content.put("president", 1);
        page3Content.put("dk", 1);
        page3Content.put("uk", 1);
        page3 = new WebPage("http://page3.com", "title3", page3Content, 4);

        setWordUSA = new HashSet<>(Set.of(page1, page2));
        setWordPresident = new HashSet<>(Set.of(page1, page2, page3));
        setWordDK = new HashSet<>(Set.of(page2, page3));
        setWordUK = new HashSet<>(Set.of(page3));
        setEmpty = new HashSet<>();

        Map<String, Set<WebPage>> mapOfTermsToPages1 = new HashMap<>(); 
        mapOfTermsToPages1.put("usa", setWordUSA);
        mapOfTermsToPages1.put("president", setWordPresident);
        mapOfTermsToPages1.put("dk", setWordDK);
        mapOfTermsToPages1.put("uk", setWordUK);

         index1 = new MockIndex(mapOfTermsToPages1);
        //index2 = new MockIndex(mapOfTermsToPages2);

         //List<WebPage> fourWordTwoOrRank = List.of(page1, page2);
    }

    // Split

    
    // Zero - Page Tests

   

    //Three - Page Tests

    @Test
    public void getRankOfWebPages_FromThreePagesWithSixTermsAndTwoORs_hasCorrectOrder () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(page3, page1, page2), queryHandler.query("   USA President   OR DK OR UK"));
    }

    @Test
    public void getRankOfWebPages_TwoTermsWithAnd_hasCorrectOrder () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(page1, page2), queryHandler.query("   USA President"));
    }

    @Test
    public void getRankOfWebPages_WithNotWronglWord_hasEmptyResult () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(), queryHandler.query("   USA President WRONG_WORD"));
    }

    @Test
    public void getRankOfWebPages_TwoTermsWithAndORWrongWord_hasCorrectOrder () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(page1, page2), queryHandler.query("   USA President OR WRONG_WORD"));
    }

    @Test
    public void getRankOfWebPages_blankQuery_hasEmptyList () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(), queryHandler.query("    "));
    }

    @Test
    public void getRankOfWebPages_withTwoWrongWords_hasEmptyResult () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(), queryHandler.query("WRONG_WORD1 OR WRONG_WORD"));
    }

    @Test
    public void getRankOfWebPages_withOnlyOr_hasEmptyResult () {
        QueryEngine queryHandler = new QueryHandler(index1, ranker);
        assertEquals(List.of(), queryHandler.query("OR"));
    }

    



}
