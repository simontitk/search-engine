package searchengine;

import java.util.List;

/**
 * An interface representing an object that processes the entered search term(s) and returns corresponding webpages.
 */
public interface QueryEngine {

    public List<WebPage> query(String term);

}
