package web.entity;

import java.util.*;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PRODUCTS {

	@Id@GeneratedValue
	@Column(name="CODE")
	private int code;
	
	@Column(name="NAME")
	private String name;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	
	@Column(name="AMOUNT")
	private int totalNum;
	
	@Column(name="DES")
	private String des;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="PRICE")
	private int price;
	
	@Column(name="IMG1")
	private String img1;
	
	@Column(name="IMG2")
	private String img2;
	
	@Column(name="IMG3")
	private String img3;
	
	@Column(name="DISABLE")
	private String disable;
	
	@OneToMany(mappedBy = "products_orders", fetch = FetchType.LAZY)
	private Collection<ORDERS> orders;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public Collection<ORDERS> getOrders() {
		return orders;
	}

	public void setOrders(Collection<ORDERS> orders) {
		this.orders = orders;
	}
	
	
	
}
