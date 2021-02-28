package Unicam.SPM2020_FMS.model;

public class Payment {

	private Integer id;
	private String type;
	
	
	public Payment(Integer id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "Payment [id=" + id + ", type=" + type + "]";
	}
	
	
	
}
