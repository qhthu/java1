package web.entity;

import javax.persistence.*;

@Entity
public class TYPE {

	@Id@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "AMOUNT")
	private int amount;
	
	@ManyToOne
	@JoinColumn(name = "CODE_PROD")
	private PRODUCTS products_type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PRODUCTS getProducts_type() {
		return products_type;
	}

	public void setProducts_type(PRODUCTS products_type) {
		this.products_type = products_type;
	}
}
