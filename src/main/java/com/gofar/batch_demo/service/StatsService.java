package com.gofar.batch_demo.service;

import com.gofar.batch_demo.entity.Order;
import com.gofar.batch_demo.utils.BatchStats;
import com.gofar.batch_demo.utils.Utilities;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StatsService {

    private List<Order> orders = new ArrayList<>();
    private BatchStats batchStats;

    public StatsService(BatchStats batchStatistics) {
        this.batchStats = batchStatistics;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Map<String, Utilities.AnalyticsSummary> getPeriodicAnalytics() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderDate().getYear() + "-" + order.getOrderDate().getMonth(),
                        Collectors.collectingAndThen(Collectors.toList(), this::calculateSummary)
                ));
    }

    public Map<String, Long> getProductDistribution() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProductName,
                        Collectors.counting()
                ));
    }

    public Map<String, Double> getRevenueByProduct() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProductName,
                        Collectors.reducing(
                                (double) 0,
                                Order::getTotalAmount,
                                Double::sum
                        )
                ));
    }

    public List<Utilities.TrendPoint> getOrderTrend() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getOrderDate,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new Utilities.TrendPoint(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(Utilities.TrendPoint::getDate))
                .toList();
    }

    public BatchStats getBatchStats() {
        return this.batchStats;
    }

    private Utilities.AnalyticsSummary calculateSummary(List<Order> periodOrders) {
        Utilities.AnalyticsSummary summary = new Utilities.AnalyticsSummary();
        summary.setTotalOrders(periodOrders.size());
        summary.setTotalRevenue(periodOrders.stream()
                .map(Order::getTotalAmount)
                .reduce((double) 0, Double::sum));
        summary.setUniqueCustomers(periodOrders.stream()
                .map(order -> order.getClient().getCode())
                .distinct()
                .count());
        return summary;
    }
}
