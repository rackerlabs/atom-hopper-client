package org.atomhopper.client;

import org.apache.abdera.Abdera;
import org.apache.abdera.protocol.Response;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.lang3.StringUtils;
import org.atomhopper.client.configuration.ReaderConfiguration;
import org.atomhopper.client.configuration.impl.DefaultReaderConfiguration;
import org.atomhopper.client.domain.FeedPage;

public class FeedReader {

    private FeedPage feedPage;

    private ReaderConfiguration config;

    public static final String BACKWARD = "backward";
    public static final String FORWARD = "forward";

    private Abdera abdera;
    private AbderaClient client;

    public FeedReader(ReaderConfiguration config) {
        this.config = config;
        abdera = new Abdera();
        client = new AbderaClient(abdera);
    }

    private FeedPage getFeedPage(String url) {
        FeedPage page;
        ClientResponse response = client.get(url);

        if (response.getType() != Response.ResponseType.SUCCESS) {
            // TODO: Handle Error
        }

        feedPage = new FeedPage(response);

        updateConfig();

        return feedPage;
    }

    private void updateConfig() {
        config.setLastMarker(feedPage.getLastPageMarker());
        config.setNextMarker(feedPage.getNextPageMarker());
        config.setPrevMarker(feedPage.getPrevPageMarker());
        config.setSelfMarker(feedPage.getSelfPageMarker());
        config.setSelfDirection(feedPage.getSelfPageDirection());
    }

    public FeedPage getFeedHeadPage() {
        return getFeedPage(buildFeedHeadLink());
    }

    public FeedPage getFeedPage() {

        if (StringUtils.isBlank(config.getSelfMarker())) {
            return getFeedHeadPage();
        }

        return getFeedPage(buildSelfLink());
    }

    public FeedPage getNextFeedPage() {
        return getFeedPage(buildNextLink());
    }

    public FeedPage getLastFeedPage() {
        return getFeedPage(buildLastLink());
    }

    public FeedPage getPrevFeedPage() {
        return getFeedPage(buildPrevLink());
    }

    private String buildFeedHeadLink() {
        StringBuilder link = new StringBuilder(config.getBaseUrl());
        link.append("?limit=").append(config.getLimit());
        return link.toString();
    }

    private String buildSelfLink() {
        return buildLink(config.getSelfMarker(), config.getSelfDirection());
    }

    private String buildNextLink() {
        return buildLink(config.getNextMarker(), BACKWARD);
    }

    private String buildLastLink() {
        return buildLink(config.getLastMarker(), BACKWARD);
    }

    private String buildPrevLink() {
        return buildLink(config.getPrevMarker(), FORWARD);
    }

    private String buildLink(String marker, String direction) {
        StringBuilder link = new StringBuilder(config.getBaseUrl());
        link.append("?limit=").append(config.getLimit());
        link.append("&marker=").append(marker);
        link.append("&direction=").append(direction);

        if (StringUtils.isNotBlank(config.getSearchTerm())) {
            link.append("&search=").append(config.getSearchTerm());
        }

        return link.toString();
    }

    private static void printout(FeedPage page) {
        System.out.println("------------------------");
        System.out.println("self: " + page.getSelfPageMarker());
        System.out.println("next   : " + page.getNextPageMarker());
        System.out.println("prev   : " + page.getPrevPageMarker());
        System.out.println("last   : " + page.getLastPageMarker());
        System.out.println("count  : " + page.getEntryCount());
        System.out.println("****");
        System.out.println(page.getEntries());
        System.out.println("****");
        System.out.println("------------------------");
    }

    public static void main(String[] args) {

        FeedPage page;

        DefaultReaderConfiguration config = new DefaultReaderConfiguration("http://localhost:8080/atomhopper-1.2.6-SNAPSHOT/feed/jdbc", 1);

        FeedReader newReader = new FeedReader(config);

        page = newReader.getFeedPage();

        printout(page);

        do {
            page = newReader.getNextFeedPage();
            printout(page);
        } while (StringUtils.isNotBlank(page.getNextPageMarker()));
    }
}
