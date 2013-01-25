package org.atomhopper.client;

import org.apache.commons.lang3.StringUtils;
import org.atomhopper.client.configuration.ReaderConfiguration;
import org.apache.abdera.model.Entry;
import org.atomhopper.client.configuration.impl.DefaultReaderConfiguration;
import org.atomhopper.client.domain.FeedPage;

import java.util.Iterator;
import java.util.List;

public class ForwardFeedReader implements Iterable<FeedPage> {

    private FeedReader reader;
    private FeedPage page;
    private boolean firstRead = true;

    private long lastBlankTime;

    private static final long MINUTE = 60000;

    public ForwardFeedReader(ReaderConfiguration config) {
        reader = new FeedReader(config);
    }

    @Override
    public Iterator<FeedPage> iterator() {
        return new ForwardFeedReaderIterator();
    }

    public class ForwardFeedReaderIterator implements Iterator<FeedPage> {

        @Override
        public boolean hasNext() {
            boolean hasnext = false;

            if (page == null) {
                page = reader.getFeedHeadPage();

                if (StringUtils.isNotBlank(page.getLastPageMarker())) {
                    page = reader.getLastFeedPage();
                    if (page.getEntryCount() > 0) {
                        hasnext = true;
                    }
                }
            } else if (StringUtils.isNotBlank(page.getPrevPageMarker())) {

                if (page.getEntryCount() > 0) {
                    hasnext = true;
                } else {
                    if (lastBlankTime == 0) {
                        lastBlankTime = System.currentTimeMillis();
                    } else {
                        if ((System.currentTimeMillis() - lastBlankTime) > MINUTE) {
                           hasnext = true;
                        }
                    }
                }
            }
            return hasnext;
        }

        @Override
        public FeedPage next() {
            if (firstRead) {
                firstRead = false;
            } else {
                page = reader.getPrevFeedPage();
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

        ForwardFeedReader newReader = new ForwardFeedReader(config);

        for (FeedPage page : newReader) {
            printout(page);
        }
    }
}
