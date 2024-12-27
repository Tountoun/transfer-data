package com.gofar.batch_demo.controller;

import com.gofar.batch_demo.service.StatsService;
import com.gofar.batch_demo.utils.BatchStats;
import com.gofar.batch_demo.utils.Utilities;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statisticsService) {
        this.statsService = statisticsService;
    }

    @GetMapping("/periodic")
    public Map<String, Utilities.AnalyticsSummary> getPeriodicAnalytics() {
        return statsService.getPeriodicAnalytics();
    }

    @GetMapping("/distribution")
    public Map<String, Long> getProductDistribution() {
        return statsService.getProductDistribution();
    }

    @GetMapping("/trend")
    public List<Utilities.TrendPoint> getOrderTrend() {
        return statsService.getOrderTrend();
    }

    @GetMapping("/global")
    public BatchStats getGlobalStats() {
        return statsService.getBatchStats();
    }
}
