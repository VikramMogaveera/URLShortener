package com.example.URLShortner.controller;

import com.example.URLShortner.model.Url;
import com.example.URLShortner.model.UrlDto;
import com.example.URLShortner.model.UserResponseDto;
import com.example.URLShortner.model.UserResponseError;
import com.example.URLShortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlService urlService;
    @PostMapping("/generate")
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlDto urlDto) {

        Url shortenUrl = urlService.generateShortLink(urlDto);

        if(shortenUrl != null) {
            UserResponseDto responseDto = new UserResponseDto();
            responseDto.setOriginalUrl(shortenUrl.getOriginalUrl());
            responseDto.setShortUrl(shortenUrl.getShortLink());
            responseDto.setExpirationDate(shortenUrl.getExpiryTime());
            return new ResponseEntity<UserResponseDto>(responseDto, HttpStatus.OK);
        }

        UserResponseError userResponseError = new UserResponseError();
        userResponseError.setStatus("404");
        userResponseError.setError("There was a an error processing you request; Please try again");
        return new ResponseEntity<UserResponseError>(userResponseError,HttpStatus.OK);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {

        if(StringUtils.isEmpty(shortLink)) {
            UserResponseError responseError = new UserResponseError();
            responseError.setStatus("400");
            responseError.setError("Invalid Url");
            return  new ResponseEntity<UserResponseError>(responseError,HttpStatus.OK);
        }
        Url urlToRet = urlService.getEncodedURL(shortLink);

        if(urlToRet == null)
        {
            UserResponseError urlErrorResponseDto = new UserResponseError();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UserResponseError>(urlErrorResponseDto,HttpStatus.OK);
        }

        if(urlToRet.getExpiryTime().isBefore(LocalDateTime.now()))
        {
            urlService.deleteShortLink(urlToRet);
            UserResponseError urlErrorResponseDto = new UserResponseError();
            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
            urlErrorResponseDto.setStatus("200");
            return new ResponseEntity<UserResponseError>(urlErrorResponseDto,HttpStatus.OK);
        }

        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
    }
}
