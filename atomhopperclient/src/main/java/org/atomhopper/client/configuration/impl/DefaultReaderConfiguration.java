package org.atomhopper.client.configuration.impl;

import org.apache.commons.lang3.StringUtils;
import org.atomhopper.client.configuration.ReaderConfiguration;

public class DefaultReaderConfiguration implements ReaderConfiguration {

    private String baseUrl;
    private String searchTerm;
    private int limit;
    private String nextMarker;
    private String prevMarker;
    private String lastMarker;
    private String selfMarker;
    private String selfDirection;

    private static final int MAX_LIMIT = 1000;
    private static final int DEFAULT_LIMIT = 25;

    public DefaultReaderConfiguration(String baseUrl) {
        this(baseUrl, DEFAULT_LIMIT);
    }

    public DefaultReaderConfiguration(String baseUrl, int limit) {
        if (StringUtils.isBlank(baseUrl)) {
            //TODO: Throw error
        }

        this.baseUrl = baseUrl;
        this.limit = limit;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String getNextMarker() {
        return nextMarker;
    }

    @Override
    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    @Override
    public String getPrevMarker() {
        return prevMarker;
    }

    @Override
    public void setPrevMarker(String prevMarker) {
        this.prevMarker = prevMarker;
    }

    @Override
    public String getLastMarker() {
        return lastMarker;
    }

    @Override
    public void setLastMarker(String lastMarker) {
        this.lastMarker = lastMarker;
    }

    @Override
    public String getSelfMarker() {
        return selfMarker;
    }

    @Override
    public void setSelfMarker(String selfMarker) {
        this.selfMarker = selfMarker;
    }

    @Override
    public String getSelfDirection() {
        return selfDirection;
    }

    @Override
    public void setSelfDirection(String selfDirection) {
        this.selfDirection = selfDirection;
    }
}
