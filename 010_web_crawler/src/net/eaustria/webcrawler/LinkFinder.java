/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria.webcrawler;

/**
 *
 * @author bmayr
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkFinder implements Runnable {

    private String url;
    private WebCrawler6 cr;
    private static boolean printed = false;
    /**
     * Used fot statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinder(String url, WebCrawler6 crawler) {
        this.url = url;
        this.cr = crawler;

    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }

    private void getSimpleLinks(String url) {
        if (cr.size() > 500 && !printed) {
            cr.printElapsedTime();
            printed = true;
        }

        if (!cr.visited(url)) {
            try {
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                System.out.println(url);

                Elements links = doc.select("a[href]");
                cr.addVisited(url);

                for (Element link : links) {
                    if (link.attr("href").startsWith("http")) {
                        try {
                            cr.queueLink(link.attr("href"));
                        } catch (Exception ex) {
                            Logger.getLogger(LinkFinder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            } catch (IOException ex) {
                System.out.println("Could not connect!!");
            }
        }
        // ToDo: Implement
        // 1. if url not already visited, visit url with linkHandler
        // 2. get url and Parse Website
        // 3. extract all URLs and add url to list of urls which should be visited
        //    only if link is not empty and url has not been visited before
        // 4. If size of link handler equals 500 -> print time elapsed for statistics               

    }
}
