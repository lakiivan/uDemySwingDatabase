package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.GenderCategory;
import model.Person;

public class Controller {

	Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void removePerson(int index) {
		db.removePerson(index);
	}
	
	public void configure(int port, String user, String password) throws Exception{
		db.configure(port, user, password);
	}
	
	public void connect() throws Exception {
		db.connect();
	}
	
	public void load() throws SQLException {
		db.load();
	}

	public void save() throws SQLException {
		db.save();
	}
	
	public void disconnect() {
		db.disconnect();
	}

	public void addPerson(FormEvent fe) {
		String name = fe.getName();
		String occupation = fe.getOccupation();
		int ageCategoryId = fe.getAgeCategory();
		String empCategoryId = fe.getEmpCat();
		boolean isUsCitizen = fe.isUsCitizen();
		String taxId = fe.getTaxId();
		String gender = fe.getGenderSelection();

		// convert int to enum age category
		AgeCategory ageCategory;
		if (ageCategoryId == 0) {
			ageCategory = AgeCategory.CHILD;
		} else if (ageCategoryId == 1) {
			ageCategory = AgeCategory.ADULT;
		} else {
			ageCategory = AgeCategory.SENIOR;
		}

		// convert string to enum employment category
		EmploymentCategory empCategory;
		if (empCategoryId.equals("employed")) {
			empCategory = EmploymentCategory.EMPLOYED;
		} else if (empCategoryId.equals("employed")) {
			empCategory = EmploymentCategory.EMPLOYED;
		} else if (empCategoryId.equals("self-employed")) {
			empCategory = EmploymentCategory.SELFEMPLOYED;
		} else if (empCategoryId.equals("unemplyed")) {
			empCategory = EmploymentCategory.UNEMPLOYED;
		} else {
			empCategory = EmploymentCategory.OTHER;
			System.err.println(empCategory);
		}

		// convert string gender to enum Gender category
		GenderCategory genderCategory;
		if (gender.equals("male")) {
			genderCategory = GenderCategory.MALE;
		} else {
			genderCategory = GenderCategory.FEMALE;
		}

		Person person = new Person(name, occupation, ageCategory, empCategory, taxId, isUsCitizen, genderCategory);

		db.addPerson(person);
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}

}
