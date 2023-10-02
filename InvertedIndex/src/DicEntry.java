public class DicEntry {
    public int term_freq;
    public int doc_freq;
    Posting pList;
    public DicEntry(int id){
        this.doc_freq = 1;
        this.term_freq = 1;
        this.pList = null;
        this.addLast(id);
    }
    public void addLast(int id){
        Posting posting = new Posting(id);
        if(this.pList == null){
            this.pList = posting;
        }
        else {
            Posting last = this.pList;
            while (last.next != null) {
                last = last.next;
            }
            last.next = posting;
        }
    }
    public Posting getTail(){
        Posting last = this.pList;
        while (last.next != null) {
            last = last.next;
        }
        return last;
    }
    public Posting getHead(){return this.pList;}

    public void print(){
        Posting curr = this.pList;
        while(curr != null){
            System.out.print("[DocId: " + curr.docId + "|term freq: " + curr.dtf + "] => ");
            curr = curr.next;
        }
    }
//    public void searchFofDoc(int id){
//        Posting ptr = this.pList;
//        boolean found = false;
//        while (ptr != null){
//            if(ptr.docId == id){
//                System.out.println("exist");
//                found = true;
//            }
//            ptr = ptr.next;
//        }
//        if(!found)
//            System.out.println("Not exist");
//    }
}
