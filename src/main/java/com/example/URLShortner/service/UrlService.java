package com.example.URLShortner.service;

import com.example.URLShortner.model.Url;
import com.example.URLShortner.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    public Url generateShortLink(UrlDto urlDto);
    public Url persistShortLink(Url url);
    public Url getEncodedURL(String url);
    public void deleteShortLink(Url url);

}
