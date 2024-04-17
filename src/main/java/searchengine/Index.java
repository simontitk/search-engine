package searchengine;

import java.util.Set;

/**
 * An interface representing an indexed dataset of terms and webpages.
 */
public interface Index {

    public Set<WebPage> getWebPagesContainingTerm(String term);

    public int getNumberOfWebPagesContainingTerm(String term);
    
    public int getNumberOfWebPages();
    
}
