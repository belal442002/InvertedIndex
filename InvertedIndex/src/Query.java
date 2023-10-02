import java.lang.Math;
import java.util.*;

public class Query {
    private InvertedList invertedList;
    private double numberOfDocuments;
    private double[] scores;
    private double[] lengths;

    public Query(InvertedList invertedList, int numberOfDocuments) {
        this.invertedList = invertedList;
        this.numberOfDocuments = numberOfDocuments;
        this.scores = new double[numberOfDocuments];
        this.lengths = new double[numberOfDocuments];
        for (int i = 0; i < numberOfDocuments; i++){
            this.scores[i] = 0;
            this.lengths[i] = 0;
        }
    }

    private double TF_IDF_tq(String term, String query){
        double tf = 0;
        for(String t : query.split("\\W+")){
            if (term.equals(t))
                tf++;
        }
        double df;
        df = this.invertedList.getIndex().get(term).doc_freq;

        return (1 + Math.log10(tf)) * Math.log10((this.numberOfDocuments/df));
    }

    private double TF_IDF_td(String term, int dtf){
        double tf = dtf;
        double df = this.invertedList.getIndex().get(term).doc_freq;
        return (1 + Math.log10(tf)) * Math.log10((this.numberOfDocuments/df));
    }

    private void Normalize(){
        for(int i = 0; i < this.numberOfDocuments; i++){
            if(Double.compare(this.scores[i], 0) == 0)
                continue;
            this.scores[i] /= Math.sqrt(this.lengths[i]);
        }
    }

    private void printRankedDocuments(){
        HashMap<Integer, Double> Documents = new HashMap<>();
        LinkedHashMap<Integer, Double> rankedDocuments = new LinkedHashMap<>();

        for (int i = 0; i < this.numberOfDocuments; i++) {
            Documents.put(i + 1, this.scores[i]);
        }

        Documents.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(entry -> rankedDocuments.put(entry.getKey(), entry.getValue()));

        for(Map.Entry<Integer, Double> rankedEntry : rankedDocuments.entrySet()){
            System.out.println("Cosin similarity for Document" + rankedEntry.getKey() + " = " + rankedEntry.getValue());
        }

        System.out.println();
        int Counter = 1;
        for(Map.Entry<Integer, Double> rankedEntry : rankedDocuments.entrySet()){
            System.out.println("Rank " + Counter++ + " Document" + rankedEntry.getKey());
        }
    }

    private boolean match(String query){
        String []terms = query.split("\\W+");
        for(String term : terms){
            term = term.toLowerCase();
            if(this.invertedList.getIndex().containsKey(term))
                return true;
        }
        return false;
    }
    public void calculate_Cosine_Similarity_Score(String query){
        if(!match(query)){
            System.out.println("No match output");
            return;
        }
        String []terms = query.split("\\W+");
        for(String term : terms){
            term = term.toLowerCase();
            if(!(this.invertedList.getIndex().containsKey(term)))
                continue;
            double WTQ = TF_IDF_tq(term, query);
            Posting current = this.invertedList.getIndex().get(term).pList;
            while(current != null){
                double WTD = TF_IDF_td(term, current.dtf);
                this.scores[current.docId - 1] += (WTQ * WTD);
                this.lengths[current.docId - 1] += Math.pow(WTD, 2);
                current = current.next;
            }
        }
        Normalize();
        printRankedDocuments();
    }

}
