package searchengine;

import java.util.Map;
import java.util.Set;



/**
 * The class represents a webpage in the dataset.
 * It contains a URL address, sitename and a collection of words appearing there, mapped to their occurence number. 
 */
public class WebPage {

    private String url;
    private String siteName;
    private Map<String, Integer> termCountTable;
    private int pageLength;


    /**
     * Constructs a WebPage object.
     * Splits the siteName into words, adds them to the termCountTable and increases the pageLength accordingly.
     * @throws IllegalArgumentException If the termCountTable is empty.
     * @param url URL of the webpage
     * @param siteName Name of the webpage
     * @param termCountTable Mapping between terms and their occurence number
     * @param pageLength Total number of words in the webpage
     */
    public WebPage(String url, String siteName, Map<String, Integer> termCountTable, int pageLength) {
        if (termCountTable.isEmpty()) {
            throw new IllegalArgumentException(url + " does not contain enough data.");
        }
        for (String siteNameWord: siteName.strip().split(" ")) {
            termCountTable.merge(siteNameWord.strip().toLowerCase(), 1, Integer::sum);
            pageLength++;
        }
        this.url = url;
        this.siteName = siteName;
        this.termCountTable = termCountTable;
        this.pageLength = pageLength;
    }


    /**
     * Retrieves the URL address of the webpage.
     * @return URL address
     */
    public String getUrl() {
        return this.url;
    }

    
    /**
    * Retireves the webpage siteName.
    * @return Webpage siteName
    */
    public String getSiteName() {
        return this.siteName;
    }


    /**
     * Retrieves the set of all terms used in the webpage.
     * @return A collection of terms from the webpage
     */
    public Set<String> getTermSet() {
        return this.termCountTable.keySet();
    }

    
    /**
     * Retrieves the total number of terms in the webpage.
     * @return The length of the webpage
     */
    public int getPageLength() {
        return this.pageLength;
    }

    
    /**
     * Retrieves the number of occurences of a term in a webpage.
     * If the term is not in the webpage, returns 0.
     * @param term The term to be searched for
     * @return The number of term occurences
     */
    public int getTermCount(String term) {
        if (!this.termCountTable.containsKey(term)) {
            return 0;
        }
        return this.termCountTable.get(term);
    }
}
