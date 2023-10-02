import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InvertedList {
    private HashMap<String,DicEntry> index = new HashMap<>();
    private int counter = 0;

    public HashMap<String, DicEntry> getIndex() {
        return index;
    }

    public void makeInvertedList(String fileName){
        this.counter++;
        File file = new File(fileName);

        try {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("[^a-zA-Z0-9]+");
            while(sc.hasNext()){
                String str = sc.next();
                str = str.toLowerCase();

                if(str.length() == 1)
                    continue;

                if(!(index.containsKey(str))){
                    index.put(str, new DicEntry(this.counter));
                }

                else {
                    DicEntry dicEntry = index.get(str);
                    dicEntry.term_freq++;
                    if(dicEntry.getTail() != null && dicEntry.getTail().docId != this.counter){
                        dicEntry.doc_freq++;
                        dicEntry.addLast(this.counter);
                    }
                    else{
                        dicEntry.getTail().dtf++;
                    }
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("\n\n\n");
    }

    public void RetrieveInfo(String term){
        if(!(this.index.containsKey(term)))
            System.out.println("this term is not exist");
        else {
            System.out.println("Term \t" + "doc_freq\t" + "term_freq\t" + "  \t\tPostingLists");
            DicEntry dicEntry = this.index.get(term);
            System.out.print("[" + term + "\t\t" + dicEntry.doc_freq + "\t\t\t" + dicEntry.term_freq + "]\t\t =>\t\t");
            dicEntry.print();
        }
    }

    public void printInvertedList(){
        System.out.println("Term \t" + "doc_freq\t" + "term_freq\t" + "  \t\tPostingLists");
        for(Map.Entry<String,DicEntry> entry : this.index.entrySet()){
            System.out.print("[" + entry.getKey() + "\t\t" + entry.getValue().doc_freq + "\t\t\t" + entry.getValue().term_freq + "]\t\t =>\t\t");
            entry.getValue().print();
            System.out.println();
        }
    }

}
