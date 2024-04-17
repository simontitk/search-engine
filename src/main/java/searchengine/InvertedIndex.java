package searchengine;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;


/**
 * The class provides a mapping between terms and webpages containg them.
 */
public class InvertedIndex implements Index {

    private Map<String, Set<WebPage>> index;
    private Generator generator;
    private int numberOfWebPages;


    /**
     * Builds the InvertedIndex by iterating through the list of webpages retrieved from the generator class field.
     * For every webpage, the set of terms is iterated through and each term is added to the inverted index map
     * as a key with a set of WebPage objects mapped to it.
     * Retrieves the number of webpages from the generator and sets it as a field value.
     * @param generator An instance of the class implementing generator interface to supply a list of WebPage objects
     */
    public InvertedIndex(Generator generator) {
        this.generator = generator;
        this.index = new HashMap<String, Set<WebPage>>();
        this.buildIndex();
    }
  

    private void buildIndex() {
        for(WebPage page: this.generator.getPages()) {
            for(String word: page.getTermSet()) {
                if(!index.containsKey(word)) {
                    index.put(word, new HashSet<WebPage>());
                }
                index.get(word).add(page);
            }
        }
        numberOfWebPages = generator.getNumberOfWebPages();
    }
    

    /**
     * Retrieves a set of WebPage objects that contain the term.
     * If no match has been found, it returns an empty set.
     * @param term The term to be searched for
     * @return Set of webpages
     */
    public Set<WebPage> getWebPagesContainingTerm(String term) {
        Set<WebPage> result = this.index.get(term);
        if (result == null) {
           return new HashSet<>();
        }
        return result;
    }


   /**
     * Retrieves the number of WebPage objects that contain the term.
     * If no match has been found, it returns 0.
     * @param term The term to be searched for
     * @return Number of matching webpages
     */
    public int getNumberOfWebPagesContainingTerm(String term) {
        return getWebPagesContainingTerm(term).size();
    }

    
    /**
     * Retrieves total number of webpages contained in the index.
     * @return Total number of webpages 
     */
    public int getNumberOfWebPages() {
        return this.numberOfWebPages;
    }
    
}