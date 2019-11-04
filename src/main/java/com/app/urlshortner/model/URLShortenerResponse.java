package com.app.urlshortner.model;

public class URLShortenerResponse {

    private String key;
    private String hostedURL;

    public URLShortenerResponse(String hostedURL, String key) {
        this.key = key;
        this.hostedURL = hostedURL;
    }

    public String getShortenedURL() {
        return hostedURL + key;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hostedURL == null) ? 0 : hostedURL.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        URLShortenerResponse other = (URLShortenerResponse) obj;
        if (hostedURL == null) {
            if (other.hostedURL != null)
                return false;
        } else if (!hostedURL.equals(other.hostedURL))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}
