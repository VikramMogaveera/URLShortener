package com.example.URLShortner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

@Entity
public class Url {

    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String originalUrl;
    private String shortLink;
    private LocalDateTime creationTime;
    private LocalDateTime expiryTime;

    public Url() {
    }

    public Url(Long id, String originalUrl, String encodedUrl, LocalDateTime creationTime, LocalDateTime expiryTime) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortLink = encodedUrl;
        this.creationTime = creationTime;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", encodedUrl='" + shortLink + '\'' +
                ", creationTime=" + creationTime +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
