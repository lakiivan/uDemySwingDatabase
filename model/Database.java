package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Connection;

public class Database {

	private List<Person> people;
	private Connection con;
	
	private int port;
	private String user;
	private String password;

	public Database() {
		super();
		this.people = new LinkedList<>();
	}
	
	public void configure(int port, String user, String password) throws Exception{
		this.port = port;
		this.user = user;
		this.password = password;
		
		if(con!= null) {
			disconnect();
			connect();
		}
	}

	public void connect() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}

		String url = "jdbc:mysql://localhost:3306/swingtest?useSSL=false";
		con = (Connection) DriverManager.getConnection(url, "root", "Omnia69Mea611");
		System.out.println("Connected: " + con);
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can not close the connection");
				e.printStackTrace();
			}
		}
	}

	public void save() throws SQLException {

		String checkSql = "select count(*) as count from people2 where id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);

		String insertSql = "insert into people2 (id, age, name, employment_status, tax_id, us_citizen, gender, occupation) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);

		String updateSql = "update people2 set age=?, name=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatment = con.prepareStatement(updateSql);

		for (Person person : people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory age = person.getAgeCategory();
			EmploymentCategory emp = person.getEmpCat();
			String tax = person.getTaxId();
			boolean isUs = person.isUsCitizen();
			GenderCategory gender = person.getGenderSelection();

			System.out.println(id);
			System.out.println(name);
			System.out.println(occupation);

			checkStmt.setInt(1, id);

			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();

			int count = checkResult.getInt(1);

			if (count == 0) {
				System.out.println("Inserting person with ID " + id);

				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, age.name());
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, emp.name());
				insertStatement.setString(col++, tax);
				insertStatement.setBoolean(col++, isUs);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);

				insertStatement.executeUpdate();

			} else {
				System.out.println("Updating person with ID " + id);
				int col = 1;
				updateStatment.setString(col++, age.name());
				updateStatment.setString(col++, name);
				updateStatment.setString(col++, emp.name());
				updateStatment.setString(col++, tax);
				updateStatment.setBoolean(col++, isUs);
				updateStatment.setString(col++, gender.name());
				updateStatment.setString(col++, occupation);
				updateStatment.setInt(col++, id);

				updateStatment.executeUpdate();

			}

		}
		updateStatment.close();
		insertStatement.close();
		checkStmt.close();

	}

	public void load() throws SQLException {
		people.clear();

		String sql = "select id, age, name, employment_status, tax_id, us_citizen, gender, occupation from people2 order by name";

		Statement selectStatement = con.createStatement();
		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			String occupation = results.getString("occupation");
			System.out.println(id + " " + name + " " + occupation + " ");

			String age = results.getString("age");
			String  emp = results.getString("employment_status");
			String taxId = results.getString("tax_id");
			String gender = results.getString("gender");
			boolean isUs = results.getBoolean("us_citizen");
			
			Person person = new Person(id, name, occupation, AgeCategory.valueOf(age),EmploymentCategory.valueOf(emp),taxId, isUs, GenderCategory.valueOf(gender));
			people.add(person);
			System.out.println(person);
			
		}

		selectStatement.close();
	}

	public void removePerson(int index) {
		people.remove(index);
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);
		oos.writeObject(persons);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Person[] persons;
		try {
			persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ois.close();
	}

}
