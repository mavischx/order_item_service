package ie.order_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(nullable = false)
	private String orderName;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private long itemId;

	@Column(nullable = false)
	private String delAddress;

	// @Column(nullable = false)
	// @Version
   	// private Long version;


	public Order(Long orderId, String orderName, int quantity, long itemId, String delAddress) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.quantity = quantity;
		this.itemId = itemId;
		this.delAddress = delAddress;
		// this.version = version;
	}

	public Order() {

	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
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

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getAddress() {
		return delAddress;
	}

	public void setAddress(String address) {
		this.delAddress = address;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + ", quantity=" + quantity + ", itemId="
				+ itemId + ", address=" + delAddress + "]";
	}

	public String getDelAddress() {
		return delAddress;
	}

	public void setDelAddress(String delAddress) {
		this.delAddress = delAddress;
	}

	// public Long getVersion() {
	// 	return version;
	// }

	// public void setVersion(Long version) {
	// 	this.version = version;
	// }

}
