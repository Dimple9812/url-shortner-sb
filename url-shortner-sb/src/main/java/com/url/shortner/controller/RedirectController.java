package com.url.shortner.controller;


import com.url.shortner.models.UrlMapping;
import com.url.shortner.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {
    private UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        UrlMapping urlMapping = urlMappingService.getOriginalUrl(shortUrl);
        if(urlMapping!= null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location" , urlMapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(httpHeaders).build();

        } else {
            return ResponseEntity.notFound().build();
        }


    }
}


//package com.url.shortner.controller;
//
//import com.url.shortner.models.UrlMapping;
//import com.url.shortner.service.UrlMappingService;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@AllArgsConstructor
//public class RedirectController {
//
//    private final UrlMappingService urlMappingService;
//
//    @GetMapping("/r/{shortUrl}")
//    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
//
//        UrlMapping urlMapping = urlMappingService.getOriginalUrl(shortUrl);
//
//        if (urlMapping != null && urlMapping.getOriginalUrl() != null) {
//
//            String originalUrl = urlMapping.getOriginalUrl();
//
//            // ✅ Ensure valid URL format
//            if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
//                originalUrl = "https://" + originalUrl;
//            }
//
//            // 🔥 Actual redirect
//            response.sendRedirect(originalUrl);
//
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//}