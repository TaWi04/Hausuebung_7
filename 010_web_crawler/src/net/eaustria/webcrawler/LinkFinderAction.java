/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author bmayr
 */
// Recursive Action for forkJoinFramework from Java7
public class LinkFinderAction extends RecursiveAction {

    private String url;
    private WebCrawler7 cr;
    private static boolean printed = false;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinderAction(String url, WebCrawler7 cr) {
        this.url = url;
        this.cr = cr;
    }

    @Override
    public void compute() {
        if (cr.size() > 500 && !printed) {
            cr.printElapsedTime();
            printed = true;
        }

        if (!cr.visited(url)) {
            List<LinkFinderAction> linkFinderActions = new ArrayList<LinkFinderAction>();
            try {
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                System.out.println(url);

                Elements links = doc.select("a[href]");
                cr.addVisited(url);

                for (Element link : links) {
                    if (link.attr("href").startsWith("http")) {
                        linkFinderActions.add(new LinkFinderAction(link.attr("href"), cr));
                    }
                }
                invokeAll(linkFinderActions);

            } catch (IOException ex) {
                System.out.println("Could not connect!!");
            }
        }
    }
}
