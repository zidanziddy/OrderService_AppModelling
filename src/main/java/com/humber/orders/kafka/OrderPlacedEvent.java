package com.humber.orders.kafka;


import java.util.Map;

public class OrderPlacedEvent {
    public OrderPlacedEvent() {}
    private Map<Long, Integer> products;
    private String orderId;


    // Constructors


    public OrderPlacedEvent(String orderId, Map<Long, Integer> products) {
        this.orderId = orderId;
        this.products = products;
    }

    // Getters and Setters

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Map<Long, Integer> getProducts() {
        return products;
    }
    public String getOrderId() {
        return orderId;
    }




    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId='" + orderId + '\'' +
                ", products=" + products +
                '}';
    }
}


