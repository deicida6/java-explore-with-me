package ru.practicum.ewm.server.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.server.stats.StatsMapper;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.repository.StatsRepository;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Transactional
    @Override
    public EndpointHitDto addRequest(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = StatsMapper.toEndpointHit(endpointHitDto);

        return StatsMapper.toEndpointHitDto(statsRepository.save(endpointHit));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStats(String start, String end, String[] uris, boolean unique) {

        LocalDateTime startTime;
        LocalDateTime endTime;
        startTime = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        endTime = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<ViewStats> list;

        if (!unique) {
            if (uris != null) {
                list = statsRepository.requestStats(startTime, endTime, uris);
            } else {
                list = statsRepository.requestAllStats(startTime, endTime);
            }
        } else {
            if (uris != null) {
                list = statsRepository.requestUniqueIpStats(startTime, endTime, uris);
            } else {
                list = statsRepository.requestUniqueIpAllStats(startTime, endTime);
            }
        }
        return list;
    }

}