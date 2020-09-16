package web.bean;

public class products {


	private String code;
	private String name;
	private String dayCreate;
	private String monthCreate;
	private String yearCreate;
	private int size;
	private int totalNum;
	private float price;
	private String img;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDayCreate() {
		return dayCreate;
	}
	public void setDayCreate(String dayCreate) {
		this.dayCreate = dayCreate;
	}
	public String getMonthCreate() {
		return monthCreate;
	}
	public void setMonthCreate(String monthCreate) {
		this.monthCreate = monthCreate;
	}
	public String getYearCreate() {
		return yearCreate;
	}
	public void setYearCreate(String yearCreate) {
		this.yearCreate = yearCreate;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public products(String code, String name, String dayCreate, String monthCreate, String yearCreate, int size,
			int totalNum, float price, String img) {
		super();
		this.code = code;
		this.name = name;
		this.dayCreate = dayCreate;
		this.monthCreate = monthCreate;
		this.yearCreate = yearCreate;
		this.size = size;
		this.totalNum = totalNum;
		this.price = price;
		this.img = img;
	}
	public products() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
