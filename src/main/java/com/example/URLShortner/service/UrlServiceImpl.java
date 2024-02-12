package com.example.URLShortner.service;

import com.example.URLShortner.model.Url;
import com.example.URLShortner.model.UrlDto;
import com.example.URLShortner.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;
    @Override
    public Url generateShortLink(UrlDto urlDto) {

        if(StringUtils.isNotEmpty(urlDto.getUrl())) {
            String encodeUrl = encodeUrl(urlDto.getUrl());
            Url urlToPersist = new Url();
            urlToPersist.setShortLink(encodeUrl);
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setCreationTime(LocalDateTime.now());
            urlToPersist.setExpiryTime(getExpirationTime(urlDto.getExpirationDate(),urlToPersist.getCreationTime()));

            return persistShortLink(urlToPersist);
        }
        return null;
    }

    private LocalDateTime getExpirationTime(String expirationDate, LocalDateTime creationTime) {

        if(StringUtils.isBlank(expirationDate)) {
            return creationTime.plusSeconds(60);
        }
        return LocalDateTime.parse(expirationDate);
    }

    private String encodeUrl(String url) {

        String encode = "";
        LocalDateTime time = LocalDateTime.now();
        encode = Hashing.murmur3_32_fixed()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return encode;
    }

    @Override
    public Url persistShortLink(Url url) {
        return urlRepository.save(url);
    }

    @Override
    public Url getEncodedURL(String url) {
        return urlRepository.findByShortLink(url);
    }

    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
