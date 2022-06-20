package gui;
import java.util.EventObject;

public class FormEvent extends EventObject {

	private String name;
	private String occupation;
	private int ageCategory;
	private String empCat;
	private String taxId;
	private boolean usCitizen;
	private String genderSelection;

	public FormEvent(Object source) {
		super(source);

	}

	public FormEvent(Object source, String name, String occupation, int ageCategory, String empCat, String taxId,
			boolean usCitizen, String genderSelection) {
		this(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.taxId = taxId;
		this.usCitizen = usCitizen;
		this.genderSelection = genderSelection;
	}

	public String getGenderSelection() {
		return genderSelection;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getEmpCat() {
		return empCat;
	}

	public int getAgeCategory() {
		return ageCategory;
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

}
