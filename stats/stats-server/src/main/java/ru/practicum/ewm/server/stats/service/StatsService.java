package ru.practicum.ewm.server.stats.service;

import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.util.List;

public interface StatsService {

    EndpointHitDto addRequest(EndpointHitDto endpointHitDto);

    List<ViewStats> getStats(String start, String end, String[] uris, boolean unique);
}