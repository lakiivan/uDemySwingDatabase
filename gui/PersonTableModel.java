package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;
	private String[] columnNames = { "ID", "Name", "Occupation", "Age Category", "Employment Category", "US Citizen",
			"Tax ID" };

	public PersonTableModel() {
	}

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 1:
			return true;
		case 4:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex < 7) {
			if (columnIndex == 0) {
				return Integer.class;
			} else if (columnIndex == 1) {
				return String.class;
			} else if (columnIndex == 2) {
				return String.class;
			} else if (columnIndex == 3) {
				return String.class;
			} else if (columnIndex == 4) {
				return EmploymentCategory.class;
			} else if (columnIndex == 5) {
				return Boolean.class;
			} else if (columnIndex == 6) {
				return String.class;
			}
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		if (db == null) {
			return;
		}

		Person person = db.get(rowIndex);

		switch (columnIndex) {
		case 1:
			person.setName((String) aValue);
			break;
		case 4:
			person.setEmpCat((EmploymentCategory) aValue);
			break;
		case 5:
			person.setUsCitizen((Boolean) aValue);
			break;
		default:
			return;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = db.get(rowIndex);

		if (columnIndex < 7) {
			if (columnIndex == 0) {
				return person.getId();
			} else if (columnIndex == 1) {
				return person.getName();
			} else if (columnIndex == 2) {
				return person.getOccupation();
			} else if (columnIndex == 3) {
				return person.getAgeCategory();
			} else if (columnIndex == 4) {
				return person.getEmpCat();
			} else if (columnIndex == 5) {
				return person.isUsCitizen();
			} else if (columnIndex == 6) {
				return person.getTaxId();
			}
		}
		return null;
	}
}
