package com.example.URLShortner.repository;

import com.example.URLShortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortLink(String shortLink);
}
