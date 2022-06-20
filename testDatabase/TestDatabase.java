package testDatabase;

import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.GenderCategory;
import model.Person;

public class TestDatabase {

	public static void main(String[] args) {

		Database db = new Database();
		try {
			db.connect();
			System.out.println("Povezao se :)");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db.addPerson(new Person("Ivan", "Engineer", AgeCategory.ADULT, EmploymentCategory.EMPLOYED, "777", true,
				GenderCategory.MALE));
		db.addPerson(new Person("Vesna", "scientist", AgeCategory.ADULT, EmploymentCategory.EMPLOYED, "", false,
				GenderCategory.FEMALE));
		db.addPerson(new Person("Joe", "lion tamer", AgeCategory.SENIOR, EmploymentCategory.EMPLOYED, "12345431", true,
				GenderCategory.MALE));
		db.addPerson(new Person("Sue", "artist", AgeCategory.CHILD, EmploymentCategory.SELFEMPLOYED, "", false,
				GenderCategory.FEMALE));

		System.out.println(db.getPeople().get(0).getName() + " id is " + db.getPeople().get(0).getId());
		System.out.println(db.getPeople().get(1).getName() + " id is " + db.getPeople().get(1).getId());
		System.out.println(db.getPeople().get(2).getName() + " id is " + db.getPeople().get(2).getId());
		System.out.println(db.getPeople().get(3).getName() + " id is " + db.getPeople().get(3).getId());

		// System.out.println(db.getPeople().get(2).getName());

		try {
			db.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			db.load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.disconnect();

	}

}
