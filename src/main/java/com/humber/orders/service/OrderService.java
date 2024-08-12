package com.humber.orders.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.humber.orders.kafka.KafkaProducerService;
import com.humber.orders.kafka.OrderPlacedEvent;
import com.humber.orders.model.Order;
import com.humber.orders.model.OrderItem;
import com.humber.orders.repository.OrderRepository;

@Service
public class OrderService {
    private final KafkaProducerService kafkaProducerService;
    private final OrderRepository orderRepository;



    private Map<Long, Integer> convertOrderItemsToMap(List<OrderItem> orderItems) {
        Map<Long, Integer> productMap = new HashMap<>();
        for (OrderItem item : orderItems) {
            productMap.put(item.getProductId(), item.getQuantity());
        }
        return productMap;
    }

    public OrderService(OrderRepository orderRepository, KafkaProducerService kafkaProducerService) {
        this.orderRepository = orderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }
    public Order placeOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        Map<Long, Integer> productMap = convertOrderItemsToMap(order.getOrderItems());
        OrderPlacedEvent event = new OrderPlacedEvent(savedOrder.getId().toString(), productMap);

        // Send event
        kafkaProducerService.sendOrderPlacedEvent(event);
        return savedOrder;
    }

}


