package web.entity;

import java.util.*;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ORDERS {
		
		@Id@GeneratedValue
		@Column(name="ID")
		private int id;
		
		@DateTimeFormat(pattern="MM/dd/yyyy")
		@Temporal(TemporalType.DATE)
		@Column(name="DATE_CREATE")
		private Date dateCreate;
		
		@Column(name="AMOUNT")
		private int amount_o;

		@ManyToOne
		@JoinColumn(name = "ID_CUS")
		private USERS users_orders;

		@ManyToOne
		@JoinColumn(name = "CODE_PROD")
		private PRODUCTS products_orders;
		
		@Column(name="STATUS")
		private String status;
		
		@Column(name="DETAIL")
		private String detail;
		
		@OneToMany(mappedBy = "orders_od_detail", fetch = FetchType.EAGER)
		private Collection<OD_DETAIL> od_detail;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Date getDateCreate() {
			return dateCreate;
		}

		public void setDateCreate(Date dateCreate) {
			this.dateCreate = dateCreate;
		}

		public int getAmount_o() {
			return amount_o;
		}

		public void setAmount_o(int amount_o) {
			this.amount_o = amount_o;
		}

		public USERS getUsers_orders() {
			return users_orders;
		}

		public void setUsers_orders(USERS users_orders) {
			this.users_orders = users_orders;
		}

		public PRODUCTS getProducts_orders() {
			return products_orders;
		}

		public void setProducts_orders(PRODUCTS products_orders) {
			this.products_orders = products_orders;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Collection<OD_DETAIL> getOd_detail() {
			return od_detail;
		}

		public void setOd_detail(Collection<OD_DETAIL> od_detail) {
			this.od_detail = od_detail;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}
		
		
		
}
