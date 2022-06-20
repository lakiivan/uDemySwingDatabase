package model;

import java.io.Serializable;

public class Person implements Serializable{
	
	private static final long serialVersionUID = 2258472556760326610L;

	//unique id counter
	private static int count = 1;
	
	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory empCat;
	private String taxId;
	private boolean usCitizen;
	private GenderCategory genderSelection;

	public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat,
			String taxId, boolean usCitizen, GenderCategory genderSelection) {
		super();
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.taxId = taxId;
		this.usCitizen = usCitizen;
		this.genderSelection = genderSelection;

		this.id = count;
		//update counter
		count++;
	}
	
	public Person(int id, String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat,
			String taxId, boolean usCitizen, GenderCategory genderSelection) {
		super();
		this.id = id;
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.taxId = taxId;
		this.usCitizen = usCitizen;
		this.genderSelection = genderSelection;
		
		//update counter
		count++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmpCat() {
		return empCat;
	}

	public void setEmpCat(EmploymentCategory empCat) {
		this.empCat = empCat;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(boolean usCitizen) {
		this.usCitizen = usCitizen;
	}

	public GenderCategory getGenderSelection() {
		return genderSelection;
	}

	public void setGenderSelection(GenderCategory genderSelection) {
		this.genderSelection = genderSelection;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", occupation=" + occupation + ", ageCategory=" + ageCategory
				+ ", empCat=" + empCat + ", taxId=" + taxId + ", usCitizen=" + usCitizen + ", genderSelection="
				+ genderSelection + "]";
	}
	
	

}
