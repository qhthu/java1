package web.entity;

import javax.persistence.*;

@Entity
public class OD_DETAIL {
	@Id@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private ORDERS orders_od_detail;
 
	@Column(name="AMOUNT")
    private int amount;
    
	@Column(name="PRICE")
	private float price;
    
	@Column(name="TOTAL")
	private float total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ORDERS getOrders_od_detail() {
		return orders_od_detail;
	}

	public void setOrders_od_detail(ORDERS orders_od_detail) {
		this.orders_od_detail = orders_od_detail;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}
