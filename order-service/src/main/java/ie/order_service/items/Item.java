package ie.order_service.items;

public class Item {

	private Long itemId;
	private String item;
	private String category;
	private Double price;

	public Item() {

	}

	public Item(Long itemId, String item, String category, Double price) {
		super();
		this.itemId = itemId;
		this.item = item;
		this.category = category;
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getMenuItem() {
		return item;
	}

	public void setMenuItem(String menuItem) {
		this.item = menuItem;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Menu [itemId=" + itemId + ", menuItem=" + item + ", price=" + price + "]";
	}

}
