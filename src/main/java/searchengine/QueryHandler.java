package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class that analyses the query, splits it into term groups and sorts the resulting webpages according to ranking score.
 */
public class QueryHandler implements QueryEngine {

    private Index index;
    private Ranker ranker;


    /**
     * Constructs a QueryHandler object.
     * @param index An instance of a class implementing the Index interface, from which term - webpages mappings can be queried.
     * @param ranker A Ranker object to rank the results of queries.
     */
    public QueryHandler(Index index, Ranker ranker) {
        this.index=index;
        this.ranker=ranker;
    }


    /**
     * Splits the search term(s) across OR separators into term groups, which are then further split into individual words,
     * resulting in a List of List of words. Unnecessay whitespaces are stripped from words in the process and each word is converted into lowercase.
     * @param term The search term(s)
     * @return An ArrayList (terms split by OR) of ArrayLists (individual words within the blocks between OR).
     */
    public static List<List<String>> splitQuery(String term) {
        term = term.replaceAll("%20", " ");
        List<List<String>> wordGroups = new ArrayList<>();
        for (String phrase : term.split("OR")){
            List<String> wordGroup = Arrays.asList(phrase.strip().toLowerCase().split("\s+"));
            wordGroups.add(wordGroup);
        }
            return wordGroups;
}
    

    /**
     * Creates a set of WebPage objects that represents an intersection of sets of WebPage objects that contain the same search term.  
     * @param listOfSets List of sets that contain search terms.
     * @return Intersection of input sets.
     */
    public static Set<WebPage> createIntersection(List<Set<WebPage>> listOfSets) {
        Set<WebPage> intersection = new HashSet<>();
            intersection.addAll(listOfSets.get(0));
            for (Set<WebPage> set: listOfSets ) {
                intersection.retainAll(set);
            }
        return intersection;
    }


    /**
     * Sorts the Entry obejcts in a Map using the double values as the sorting parameter. Returns a List of WebPages in descending order.
     * @param pagesMap Webpages mapped to the ranking score
     * @return A sorted webpage list.
     */
    public static List<WebPage> sortPageMap(Map<WebPage,Double> pagesMap) {
        List<Map.Entry<WebPage, Double>> sortingList = new ArrayList<>(pagesMap.entrySet());
        List<WebPage> sortedList = new ArrayList<>();
        sortingList.sort(Map.Entry.comparingByValue());
        for (int i = sortingList.size()-1; i >= 0; i--) {
            sortedList.add(sortingList.get(i).getKey());
            }
            return sortedList;
    }
    
    /**
     * Retrieves a list of webpages from the index field of the class based on a complex query.
     * Multiple terms queried with an "OR" separator are split into groups, which are processed separately.
     * Each word in such a group is queried from the inverted index, then the intersection of the resulting sets is determined.
     * Each webpage in the intersection is ranked according to the selected method.
     * The union of the intersections is determined, where webpages appearing in multiple intersections retain their highest score.
     * The resulting collection is then sorted according to the scores.
     * @param term The term to be queried from the inverted index.
     * @return The sorted list of webpages according to the selected ranking method.
     */
    public List<WebPage> query(String term) {
        if (term.isBlank()) {
            return new ArrayList<>();
        }
        List<List<String>> wordGroups = splitQuery(term);
        Map<WebPage, Double> webPageComparingMap = new HashMap<>();
        for (List<String> wordGroup : wordGroups) {
            List<Set<WebPage>> setsContainingTerms = new ArrayList<>();
            for (String word : wordGroup) {
                Set<WebPage> setContainingTerm = this.index.getWebPagesContainingTerm(word);
                setsContainingTerms.add(setContainingTerm);
            }
            Set<WebPage> intersection = createIntersection(setsContainingTerms);
            for (WebPage page : intersection) {
                double pageScore = 0;
                for (String word : wordGroup) {
                    pageScore += ranker.tf_idf(page.getTermCount(word),
                                                page.getPageLength(),
                                                this.index.getNumberOfWebPages(),
                                                this.index.getNumberOfWebPagesContainingTerm(word));
                }
                if ((!webPageComparingMap.containsKey(page)) || (webPageComparingMap.get(page) < pageScore)) {
                    webPageComparingMap.put(page, pageScore);
                }
            }
        }      
        return sortPageMap(webPageComparingMap);
    }
    
}
