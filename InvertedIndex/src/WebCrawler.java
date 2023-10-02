import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WebCrawler {
    private Set<String> links = new HashSet<>();
    private static final int MAX_DEPTH = 2; // Maximum depth for crawling

    public void getPageLinks(String URL, int depth) {
        if (depth > MAX_DEPTH) {
            return;
        }
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)

        if (!links.contains(URL)) {
            try {
                if (links.add(URL)) {
                    System.out.println(URL);
                }
                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get(); //jsoup jar to extract web
                //3. Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");

                //5. For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth + 1);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}
