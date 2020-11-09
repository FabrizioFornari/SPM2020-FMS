package jbr.springmvc.model;

public class Municipality extends User{
	
	private String taxCode;
	private int authNumber;
	
	public Municipality(int idUser, String password, String userType) {
		super(idUser, password, userType);
		// TODO Auto-generated constructor stub
	}

	public Municipality(int idUser, String password, String userType, String taxCode, int authNumber) {
		super(idUser, password, userType);
		this.taxCode = taxCode;
		this.authNumber = authNumber;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public int getAuthNumber() {
		return authNumber;
	}

	public void setAuthNumber(int authNumber) {
		this.authNumber = authNumber;
	}

	@Override
	public String toString() {
		return "Municipality [taxCode=" + taxCode + ", authNumber=" + authNumber + ", getIdUser()=" + getIdUser()
				+ ", getPassword()=" + getPassword() + ", getUserType()=" + getUserType() + "]";
	}

}
