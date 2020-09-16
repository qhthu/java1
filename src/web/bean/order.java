package web.bean;

public class order {

	
	private int id;
	
	private int dayCreate;
	
	private int monthCreate;
	
	private int yearCreate;
	
	private int amount;

	private String user;
	
	private int product_od;

	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDayCreate() {
		return dayCreate;
	}

	public void setDayCreate(int dayCreate) {
		this.dayCreate = dayCreate;
	}

	public int getMonthCreate() {
		return monthCreate;
	}

	public void setMonthCreate(int monthCreate) {
		this.monthCreate = monthCreate;
	}

	public int getYearCreate() {
		return yearCreate;
	}

	public void setYearCreate(int yearCreate) {
		this.yearCreate = yearCreate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getProduct_od() {
		return product_od;
	}

	public void setProduct_od(int product_od) {
		this.product_od = product_od;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public order(int id, int dayCreate, int monthCreate, int yearCreate, int amount, String user, int product_od,
			String status) {
		super();
		this.id = id;
		this.dayCreate = dayCreate;
		this.monthCreate = monthCreate;
		this.yearCreate = yearCreate;
		this.amount = amount;
		this.user = user;
		this.product_od = product_od;
		this.status = status;
	}

	public order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
