package org.atomhopper.client;

import org.atomhopper.client.configuration.ReaderConfiguration;
import org.apache.abdera.model.Entry;
import org.atomhopper.client.domain.FeedPage;

import java.util.Iterator;
import java.util.List;

public class ForwardFeedReader implements Iterable<FeedPage> {

    FeedReader reader;

    public ForwardFeedReader(ReaderConfiguration config) {
        reader = new FeedReader(config);
    }

    @Override
    public Iterator<FeedPage> iterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
