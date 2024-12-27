package com.gofar.batch_demo.config;

import com.gofar.batch_demo.entity.Client;
import com.gofar.batch_demo.entity.Order;
import com.gofar.batch_demo.model.DataRow;
import com.gofar.batch_demo.model.ProcessedData;
import com.gofar.batch_demo.service.StatsService;
import com.gofar.batch_demo.utils.BatchStats;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe de traitement des informations lues depuis le fichier excel.
 * Un cache des clients est utilisé pour éviter de traiter un même client plusieurs fois
 */
@Component
public class DataProcessor implements ItemProcessor<DataRow, ProcessedData> {

    private final Map<String, Client> clientCache = new HashMap<>();
    private final Set<String> productCodes = new HashSet<>();
    private final BatchStats batchStats;
    private StatsService statsService;

    public DataProcessor(BatchStats batchStats) {
        this.batchStats = batchStats;
    }


    @Override
    public ProcessedData process(DataRow data) {
        try {

            this.batchStats.getTotalRowsRead().incrementAndGet();

            Client client = clientCache.computeIfAbsent(data.getClientCode(), code -> {
                Client newClient = new Client();
                newClient.setCode(code);
                newClient.setAddress(data.getClientAddress());
                newClient.setEmail(data.getClientEmail());
                newClient.setName(data.getClientName());
                this.batchStats.getUniqueCients().add(code);

                return newClient;
            });

            Order order = new Order();
            order.setNumber(data.getOrderNumber());
            order.setOrderDate(data.getOrderDate());
            order.setClient(client);
            order.setProductCode(data.getProductCode());
            order.setProductName(data.getProductName());
            order.setQuantity(data.getQuantity());
            order.setUnitPrice(data.getUnitPrice());
            order.setTotalAmount(data.getUnitPrice() * data.getQuantity());

            this.batchStats.getTotalOrdersProcessed().incrementAndGet();
            this.batchStats.addOrderValue(order.getTotalAmount());
            this.statsService.addOrder(order);

            if (this.productCodes.add(data.getProductCode())) {
                this.batchStats.getTotalProducts().incrementAndGet();
            }

            client.getOrders().add(order);

            return new ProcessedData(client, order);
        } catch (Exception e) {
            this.batchStats.getErrorCount().incrementAndGet();
            throw e;
        }
    }

    @Autowired
    public void setStatsService(StatsService statsService) {
        this.statsService = statsService;
    }
}
