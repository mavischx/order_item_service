package ie.order_service.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ie.order_service.items.Item;

@JsonPropertyOrder({ "orderId", "orderName", "delAddress", "quantity", "item" })

public class OrderResponse {

	private long orderId;
	private String orderName;
	private int quantity;
//	private long itemId;
	private String delAddress;
	private Item item;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDelAddress() {
		return delAddress;
	}

	public void setDelAddress(String delAddress) {
		this.delAddress = delAddress;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "OrderResponse [orderId=" + orderId + ", orderName=" + orderName + ", quantity=" + quantity
				+ ", delAddress=" + delAddress + ", item=" + item + "]";
	}

	public OrderResponse(Order order, Item item) {
		super();
		this.orderId = order.getOrderId();
		this.orderName = order.getOrderName();
		this.quantity = order.getQuantity();
		this.delAddress = order.getAddress();
		this.item = item;
	}

}
