package com.url.shortner.service;
import com.url.shortner.dtos.ClickEventDTO;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.models.ClickEvent;
import com.url.shortner.models.UrlMapping;
import com.url.shortner.models.User;
import com.url.shortner.repository.ClickEventRepository;
import com.url.shortner.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;
    private ClickEventRepository clickEventRepository;

   public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }
//   public UrlMappingDto createShortUrl(String originalUrl, User user) {
//
//       // ✅ FIX: normalize URL
//       if (originalUrl != null &&
//               !originalUrl.startsWith("http://") &&
//               !originalUrl.startsWith("https://")) {
//
//           originalUrl = "https://" + originalUrl;
//       }
//
//       String shortUrl = generateShortUrl();
//
//       UrlMapping urlMapping = new UrlMapping();
//       urlMapping.setOriginalUrl(originalUrl); // ✅ now always valid
//       urlMapping.setShortUrl(shortUrl);
//       urlMapping.setUser(user);
//       urlMapping.setCreatedAt(LocalDateTime.now());
//
//       UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
//
//       return convertToDto(savedUrlMapping);
//   }

    private UrlMappingDto convertToDto(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto = new UrlMappingDto();
        urlMappingDto.setId(urlMapping.getId());
        urlMappingDto.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
        urlMappingDto.setClickCount(urlMapping.getClickCount());
        //urlMappingDto.setCreatedDate(urlMapping.getCreatedDat);
        urlMappingDto.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDto;
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //length of short url set to 8, then generate any random char , append till length is 8
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);
         for(int i =0; i<8 ; i++){
             shortUrl.append(characters.charAt(random.nextInt(characters.length())));
         }
        return shortUrl.toString();
    }

    public List<UrlMappingDto> getUrlByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map((this::convertToDto))
                .collect(Collectors.toList());
    }

    //transforming o/p getting from the repository method
    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
            List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
            List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
            return clickEvents.stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));

        }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping!=null){
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            urlMappingRepository.save(urlMapping);

            //Record click event
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepository.save(clickEvent);
        }
        return urlMapping;
    }
}
