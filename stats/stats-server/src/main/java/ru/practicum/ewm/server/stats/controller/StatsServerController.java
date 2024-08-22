package ru.practicum.ewm.server.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.server.stats.service.StatsService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsServerController {

    private final StatsService statsService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    public EndpointHitDto addUser(
            @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Creating request {}, app={}, ip={}", endpointHitDto.getApp(), endpointHitDto.getIp());
        return statsService.addRequest(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(
            @RequestParam(name = "start") String startString,
            @RequestParam(name = "end") String endString,
            @RequestParam(required = false, name = "uris") String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") boolean unique) throws UnsupportedEncodingException {

        log.info("Get stats");

        LocalDateTime start = LocalDateTime.parse(URLDecoder.decode(startString, StandardCharsets.UTF_8), formatter);
        LocalDateTime end = LocalDateTime.parse(URLDecoder.decode(endString, StandardCharsets.UTF_8), formatter);

        return statsService.getStats(start, end, uris, unique);
    }

}