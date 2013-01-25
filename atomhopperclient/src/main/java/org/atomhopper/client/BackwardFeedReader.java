package org.atomhopper.client;

import org.apache.commons.lang3.StringUtils;
import org.atomhopper.client.configuration.ReaderConfiguration;
import org.atomhopper.client.configuration.impl.DefaultReaderConfiguration;
import org.atomhopper.client.domain.FeedPage;

import java.util.Iterator;

public class BackwardFeedReader implements Iterable<FeedPage> {

    private FeedReader reader;
    private FeedPage page;
    private boolean firstRead = true;

    public BackwardFeedReader(ReaderConfiguration config) {
        reader = new FeedReader(config);
    }

    @Override
    public Iterator<FeedPage> iterator() {
        return new BackwardFeedReaderIterator();
    }

    public class BackwardFeedReaderIterator implements Iterator<FeedPage> {

        @Override
        public boolean hasNext() {
            boolean hasnext = false;

            if (page == null) {
                page = reader.getFeedPage();
                if (page.getEntryCount() > 0) {
                    hasnext = true;
                }
            } else if (StringUtils.isNotBlank(page.getNextPageMarker())) {
                hasnext = true;
            }
            return hasnext;
        }

        @Override
        public FeedPage next() {
            if (firstRead) {
                firstRead = false;
            } else {
                page = reader.getNextFeedPage();
            }
            return page;
        }

        @Override
        public void remove() {
            //no-op
        }
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

        DefaultReaderConfiguration config = new DefaultReaderConfiguration("http://localhost:8080/atomhopper-1.2.6-SNAPSHOT/feed/jdbc", 1);

        BackwardFeedReader newReader = new BackwardFeedReader(config);

        for (FeedPage page : newReader) {
            printout(page);
        }
    }
}
