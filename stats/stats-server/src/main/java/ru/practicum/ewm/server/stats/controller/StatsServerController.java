package ru.practicum.ewm.server.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.server.stats.service.StatsService;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsServerController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public EndpointHitDto addUser(
            @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Creating request {}, app={}, ip={}", endpointHitDto.getApp(), endpointHitDto.getIp());
        return statsService.addRequest(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "start") String start,
                                    @RequestParam(name = "end") String end,
                                    @RequestParam(required = false, name = "uris") String[] uris,
                                    @RequestParam(name = "unique", defaultValue = "false") boolean unique) throws UnsupportedEncodingException {
        log.info("Get stats");
        return statsService.getStats(start, end, uris, unique);
    }

}