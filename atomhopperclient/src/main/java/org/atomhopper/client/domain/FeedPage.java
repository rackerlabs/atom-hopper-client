package org.atomhopper.client.domain;

import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.ClientResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedPage {

    private final static String LAST_LINK = "last";
    private final static String NEXT_LINK = "next";
    private final static String PREVIOUS_LINK = "previous";
    private final static String SELF_LINK = "self";

    private Feed feed;

    public FeedPage(ClientResponse response) {
        Document<Feed> doc = response.getDocument();
        feed = doc.getRoot();
    }

    public String getLastPageMarker() {
        return getPageMarker(LAST_LINK);
    }

    public String getNextPageMarker() {
        return getPageMarker(NEXT_LINK);
    }

    public String getPrevPageMarker() {
        return getPageMarker(PREVIOUS_LINK);
    }

    public String getSelfPageMarker() {
        return getPageMarker(SELF_LINK);
    }

    public String getSelfPageDirection() {
        return getPageDirection(SELF_LINK);
    }

    public List<Entry> getEntries() {
        List<Entry> entries = new ArrayList<Entry>();
        if (feed != null) {
            entries = feed.getEntries();
        }
        return Collections.unmodifiableList(entries);
    }

    public List<Entry> getEntry(String entryId) {
        List<Entry> entries = new ArrayList<Entry>();
        if (feed != null) {
            Entry entry = feed.getEntry(entryId);
            if (entry != null) {
                entries.add(feed.getEntry(entryId));
            }
        }
        return Collections.unmodifiableList(entries);
    }

    public int getEntryCount() {
        return feed == null ? 0 : feed.getEntries().size();
    }

    private String getLink(String name) {
        String url = "";
        if (feed != null) {
            Link link = feed.getLink(name);
            if (link != null) {
                url = link.getHref().toString();
            }
        }
        return url;
    }

    private String getPageMarker(String name) {
        String marker = "";
        if (feed != null) {
            Link link = feed.getLink(name);
            if (link != null) {
                marker = getValueFromQueryString(link.getHref().getQuery(), "marker");
            }
        }
        return marker;
    }

    private String getPageDirection(String name) {
        String marker = "";
        if (feed != null) {
            Link link = feed.getLink(name);
            if (link != null) {
                marker = getValueFromQueryString(link.getHref().getQuery(), "direction");
            }
        }
        return marker;
    }

    private String getValueFromQueryString(String queryString, String key) {
        String marker = "";
        String[] values = queryString.split("&");
        for (String value : values)
        {
            String[] param = value.split("=");
            if (param[0].equals(key)) {
                marker = param[1];
            }
        }
        return marker;
    }
}
