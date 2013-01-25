package org.atomhopper.client.configuration;

public interface ReaderConfiguration {
    String getBaseUrl();
    void setBaseUrl(String baseUrl);
    String getSearchTerm();
    void setSearchTerm(String searchTerm);
    int getLimit();
    void setLimit(int limit);
    String getNextMarker();
    void setNextMarker(String nextMarker);
    String getPrevMarker();
    void setPrevMarker(String prevMarker);
    String getLastMarker();
    void setLastMarker(String lastMarker);
    String getSelfMarker();
    void setSelfMarker(String selfMarker);
    String getSelfDirection();
    void setSelfDirection(String selfDirection);
}
