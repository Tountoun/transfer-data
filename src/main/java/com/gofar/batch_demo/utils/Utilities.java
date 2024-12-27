package com.gofar.batch_demo.utils;

import java.time.LocalDate;

public class Utilities {

    private Utilities() {
        // Private constructor
    }

    public static class AnalyticsSummary {
        private int totalOrders;
        private Double totalRevenue;
        private long uniqueCustomers;


        public int getTotalOrders() {
            return totalOrders;
        }

        public void setTotalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
        }

        public Double getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(Double totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public long getUniqueCustomers() {
            return uniqueCustomers;
        }

        public void setUniqueCustomers(long uniqueCustomers) {
            this.uniqueCustomers = uniqueCustomers;
        }
    }

    public static class TrendPoint {
        private final LocalDate date;
        private final long count;


        public TrendPoint(LocalDate date, long count) {
            this.date = date;
            this.count = count;
        }

        public long getCount() {
            return count;
        }

        public LocalDate getDate() {
            return date;
        }
    }

}
