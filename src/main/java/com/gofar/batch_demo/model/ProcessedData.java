package com.gofar.batch_demo.model;

import com.gofar.batch_demo.entity.Client;
import com.gofar.batch_demo.entity.Order;

/**
 * Classe pour la gestion des données transformées
 */
public class ProcessedData {

    private Client client;
    private Order order;

    public ProcessedData() {

    }

    public ProcessedData(Client client, Order order) {
        this.client = client;
        this.order = order;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
