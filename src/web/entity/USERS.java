package web.entity;

import java.util.*;

import javax.persistence.*;

@Entity
public class USERS {

	@Id@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="FULLNAME")
	private String fullname;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="DISABLE")
	private String disable;
	
	@OneToMany(mappedBy = "users_orders", fetch = FetchType.LAZY)
	private Collection<ORDERS> orders;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
