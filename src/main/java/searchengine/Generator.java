package searchengine;

import java.util.List;


/**
 * An interface representing a builder of WebPage objects from a provided datasource.
 */
public interface Generator {

        public List<WebPage> getPages();

        public int getNumberOfWebPages();
        
}
