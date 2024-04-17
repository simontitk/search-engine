package searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is the entry point of the search engine application.
 */
public class App {
    
    /**
     * Sets up the application by initialising WebPageGenerator, an InvertedIndex, a Ranker, QueryHandler and WebServer objects, and then starts the server.
     * Catches various exceptions which may arise as a result of errors in datafiles.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        try {
            final int PORT = 8080;
            final String PAGEMARKER ="*PAGE:";
            final String FILENAME = Files.readString(Paths.get("config.txt")).strip();
    
            Generator generator = new WebPageGenerator(FILENAME, PAGEMARKER);
            Index index = new InvertedIndex(generator);
            Ranker ranker = new Ranker();
            QueryEngine queryEngine = new QueryHandler(index, ranker);
            WebServer webServer = new WebServer(PORT, queryEngine);
    
            webServer.startServer();
            
        } 
        catch (NullPointerException npe) {
            System.out.println("Input data file is empty. Server not started.");
            
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Input data file is not found. Server not started.");
            
        }
        catch (IOException ioe) {
            System.out.println("config.txt is not found. Server not started.");
            
        }
    }
}
