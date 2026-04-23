package com.url.shortner.controller;

import com.url.shortner.dtos.ClickEventDTO;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.models.User;
import com.url.shortner.service.UrlMappingService;
import com.url.shortner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;

//    @PostMapping("/shorten")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String,String> request,
//                                                        Principal principal){
//        //passing our long url in form of key value pair(":")
//        String originalUrl = request.get("originalUrl");
//        //every url associated with user id so we need user info
//       User user = userService.findByUsername(principal.getName());
//       //call service method
//        UrlMappingDto urlMappingDto = urlMappingService.createShortUrl(originalUrl, user);
//        return ResponseEntity.ok(urlMappingDto);
    //}
@PostMapping("/shorten")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<?> createShortUrl(
        @RequestBody Map<String, String> request,
        Principal principal
) {

    String originalUrl = request.get("originalUrl");

    if (originalUrl == null || originalUrl.trim().isEmpty()) {
        return ResponseEntity.badRequest().body("originalUrl is required");
    }

    if (principal == null) {
        return ResponseEntity.status(401).body("User not authenticated");
    }

    User user = userService.findByUsername(principal.getName());

    UrlMappingDto urlMappingDto =
            urlMappingService.createShortUrl(originalUrl, user);

    return ResponseEntity.ok(urlMappingDto);
}

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDto>> getUserUrls(Principal principal){
        //getting user obj from security context,finding user using principal
        User user = userService.findByUsername(principal.getName());
        //getting all urls for a user using service class
        List<UrlMappingDto> urls = urlMappingService.getUrlByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);

    }
    @GetMapping("/totalClicks")
    //@PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user = userService.findByUsername(principal.getName()) ;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);

    }

}
