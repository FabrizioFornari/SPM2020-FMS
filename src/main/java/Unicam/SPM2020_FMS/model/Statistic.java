package Unicam.SPM2020_FMS.model;

public class Statistic {
	
	private String description;
	private Float quantity;
	private Float percentage;

	public Statistic() {
	}	

	public Statistic(String description, Float quantity) {
		this.description = description;
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

}
