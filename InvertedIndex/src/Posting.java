public class Posting {
    public int docId;
    public Posting next;
    public int dtf;

    public Posting(int id){
        docId = id;
        dtf = 1;
        next = null;
    }
}
