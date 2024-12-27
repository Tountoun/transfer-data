package com.gofar.batch_demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe de gestion des statistiques
 */
public class BatchStats {

    private LocalDateTime statTime;
    private LocalDateTime endTime;
    private AtomicInteger totalRowsRead = new AtomicInteger(0);
    private AtomicInteger totalOrdersProcessed = new AtomicInteger(0);
    private Set<String> uniqueClients = new HashSet<>();
    private AtomicInteger totalProducts = new AtomicInteger(0);
    private Double totalOrderValue = 0D;
    private AtomicInteger errorCount = new AtomicInteger(0);

    public void start() {
        this.statTime = LocalDateTime.now();
    }

    public void end() {
        this.endTime = LocalDateTime.now();
    }

    public Duration getDuration() {
        return Objects.nonNull(this.endTime) && Objects.nonNull(this.statTime) ? Duration.between(statTime, endTime) : Duration.ZERO;
    }

    public synchronized  void addOrderValue(Double value) {
        this.totalOrderValue += value;
    }


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AtomicInteger getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(AtomicInteger errorCount) {
        this.errorCount = errorCount;
    }

    public LocalDateTime getStatTime() {
        return statTime;
    }

    public void setStatTime(LocalDateTime statTime) {
        this.statTime = statTime;
    }

    public AtomicInteger getTotalOrdersProcessed() {
        return totalOrdersProcessed;
    }

    public void setTotalOrdersProcessed(AtomicInteger totalOrdersProcessed) {
        this.totalOrdersProcessed = totalOrdersProcessed;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public AtomicInteger getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(AtomicInteger totalProducts) {
        this.totalProducts = totalProducts;
    }

    public AtomicInteger getTotalRowsRead() {
        return totalRowsRead;
    }

    public void setTotalRowsRead(AtomicInteger totalRowsRead) {
        this.totalRowsRead = totalRowsRead;
    }

    public Set<String> getUniqueCients() {
        return uniqueClients;
    }

    public void setUniqueCients(Set<String> uniqueCients) {
        this.uniqueClients = uniqueCients;
    }
}
