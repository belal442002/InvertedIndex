import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        InvertedList invertedList = new InvertedList();
        List<String> list = new ArrayList<>();

        for (int i = 1; i <=10 ; i++) {
            String file = "Doc" + String.valueOf(i) + ".txt";
            list.add(file);
        }
        for(String fileName : list){
            invertedList.makeInvertedList(fileName);
        }

        while(true){
            System.out.println("\nChoose option: ");
            System.out.println("1- Retrieve term info.");
            System.out.println("2- print inverted list.");
            System.out.println("3- Enter query.");
            System.out.println("4- Web crawler.");
            System.out.println("5- Exit");
            System.out.println("Option: ");
            int opt = in.nextInt();

                if(opt == 1) {
                    while(true){
                        System.out.println("Enter term name: ");
                        String termName = new Scanner(System.in).nextLine();
                        invertedList.RetrieveInfo(termName);
                        System.out.println("\n\n\nSearch for another term?");
                        System.out.println("1- Yes");
                        System.out.println("2- No");
                        int ch = in.nextInt();
                        if (ch == 2)
                            break;
                    }
                }
                else if(opt == 2)
                    invertedList.printInvertedList();
                else if(opt == 3){
                    System.out.print("Query: ");
                    String q = new Scanner(System.in).nextLine();
                    Query query = new Query(invertedList, 10);
                    query.calculate_Cosine_Similarity_Score(q);
                }
                else if(opt == 4){
                    WebCrawler webCrawler = new WebCrawler();
                    webCrawler.getPageLinks("https://example.com/", 1);
                }
                else
                    break;
        }

    }
}