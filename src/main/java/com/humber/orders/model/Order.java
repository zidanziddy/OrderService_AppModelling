package com.humber.orders.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {

	@Column(nullable = false)
	private BigDecimal totalPrice;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

	public Long getId() {
		return id;
	}



	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}


	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
    
}

