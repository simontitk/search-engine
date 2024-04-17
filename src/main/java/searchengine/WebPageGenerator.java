package searchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of the class is parsing a textfile containing data of webpages into a list of WebPage object.
 */
public class WebPageGenerator implements Generator {

    private List<WebPage> pages;
    private String filename;
    private String pageMarker;

    /**
     * Constructs a WebPageGenerator object and initializes the file reading.
     * @param filename: The name of the file to be parsed.
     * @param pageMarker The separator string used to denote where a new webpage starts in the textfile.
     * @throws FileNotFoundException If the textfile with the given filename does not exist.
     * @throws IOException If an error occurs during reading the textfile.
     */
    public WebPageGenerator(String filename, String pageMarker) throws FileNotFoundException, IOException {
        this.filename = filename;
        this.pageMarker = pageMarker;
        this.pages = new ArrayList<>();
        this.generate();
    }

    /**
     * Return the list of WebPage objects contained in the class field.
     * @return List of WebPages
     */
    public List<WebPage> getPages() {
        return this.pages;
    }

    /**
     * Return the total number of WebPage objects contained in the class field.
     */
    public int getNumberOfWebPages() {
        return this.pages.size();
    }


    private void generate() throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String url = "";
        
        do {
            url = reader.readLine();
            if (url == null) {
                reader.close();
                throw new NullPointerException();
            }
            
        } while ((!url.contains(pageMarker)));

        String siteName = reader.readLine();

        String line = "";
        int pageLength = 0;
        Map<String, Integer> termCountTable = new HashMap<>();
        
        outerLoop:
        while (line != null) {
            line = reader.readLine();
            if (line == null || line.startsWith(pageMarker)) {
                try {
                    WebPage page = new WebPage(url.substring(pageMarker.length()), siteName, termCountTable, pageLength);
                    pages.add(page);  
                }
                catch (IllegalArgumentException e) {
                }
                finally {
                    url = line;
                    termCountTable = new HashMap<>();
                    pageLength = 0;
                    siteName = reader.readLine();
                    if (siteName == null) {
                        break;
                    }
                    while (siteName.startsWith(pageMarker)) {
                        url = siteName;
                        siteName = reader.readLine();
                        if (siteName == null) {
                            break outerLoop;
                        }
                    }
                }
            }
            else {
                termCountTable.merge(line.toLowerCase(), 1, Integer::sum);
                pageLength++;
            }
        }
        reader.close();
    }

}