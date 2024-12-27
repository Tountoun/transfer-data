package com.gofar.batch_demo.config;

import com.gofar.batch_demo.model.ProcessedData;
import com.gofar.batch_demo.repository.ClientRepository;
import com.gofar.batch_demo.repository.OrderRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomItemWriter implements ItemWriter<ProcessedData> {


    private ClientRepository clientRepository;
    private OrderRepository orderRepository;

    @Override
    public void write(Chunk<? extends ProcessedData> items) throws Exception {
        Set<String> processedClientCodes = new HashSet<>();

        for (ProcessedData item : items) {
            if (processedClientCodes.add(item.getClient().getCode())) {
                clientRepository.save(item.getClient());
            }
            orderRepository.save(item.getOrder());
        }
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
