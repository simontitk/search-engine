package searchengine;

/**
 * The class that calculates page ranking metrics.
 */
public class Ranker {
    
    public Ranker() {}

    
    /**
     * Calculates the Term frequency for given inputs (https://en.wikipedia.org/wiki/Tf%E2%80%93idf#Term_frequency)
     * @param termCount Number of occurences of the term in a webpage
     * @param pageLength Total number of words in a webpage
     * @return Calculated Term frequency
     */
    public double tf(int termCount, int pageLength) {
       return (double)termCount / (double)pageLength;
    }

    
   /**
    * Calculates the Inverse document frequency for given inputs (https://en.wikipedia.org/wiki/Tf%E2%80%93idf#Inverse_document_frequency)
    * @param numberOfWebPages Total number of webpages in the dataset
    * @param numberOfWebPagesContainingTerm Total number of webpages that contain the search term
    * @return Calculated Inverted document frequency
    */
    public double idf(int numberOfWebPages, int numberOfWebPagesContainingTerm) {
        return Math.log10((1 + (double)numberOfWebPages) / 
                          (1 + (double)numberOfWebPagesContainingTerm));
    }

    
    /**
     * Calculates the Term frequency-inverse document frequency for given inputs (https://en.wikipedia.org/wiki/Tf%E2%80%93idf#Term_frequency%E2%80%93inverse_document_frequency)
     * @param termCount Number of occurences of the term in a webpage
     * @param pageLength Total number of words in a webpage
     * @param numberOfWebPages Total number of webpages in the dataset
     * @param numberOfWebPagesContainingTerm Total number of webpages that contain the search term
     * @return Calculated term frequency-inverted document frequency
     */
    public double tf_idf(int termCount, int pageLength, int numberOfWebPages, int numberOfWebPagesContainingTerm) {
        return tf(termCount, pageLength) * idf(numberOfWebPages, numberOfWebPagesContainingTerm);
    }

}
