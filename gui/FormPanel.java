package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okButton;
	private FormListener formListener;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;
	
	private ButtonGroup genderGroup;
	private JRadioButton maleRadio;
	private JRadioButton femaleradio;
	
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		//set all panel components
		setLabelsAndTextFields();
		setAgeList();
		setOkBtn();
		setEmpCombo();
		setCitizenCheckBox();
		setRadioBuittons();

		// set layout for the panel
		setLayout();

	}

	private void setRadioBuittons() {
		maleRadio = new JRadioButton("male");
		maleRadio.setSelected(true);
		femaleradio = new JRadioButton("fermale");
		genderGroup = new ButtonGroup();
		genderGroup.add(maleRadio);
		genderGroup.add(femaleradio);
		maleRadio.setActionCommand("male");
		femaleradio.setActionCommand("female");
	}

	private void setCitizenCheckBox() {
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax id: ");
		taxField.setEnabled(false);
		taxLabel.setEnabled(false);
		citizenCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxField.setEnabled(isTicked);
				taxLabel.setEnabled(isTicked);
				
			}
		});		
	}

	private void setEmpCombo() {
		empCombo = new JComboBox<>();
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setBackground(Color.white);
	}

	private void setLabelsAndTextFields() {
		// labels and text fields
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
	}

	private void setAgeList() {
		ageList = new JList<>();
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 - 65"));
		ageModel.addElement(new AgeCategory(2, "Over 65"));
		ageList.setModel(ageModel);
		ageList.setPreferredSize(new Dimension(110, 66));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
	}

	private void setOkBtn() {
		// ok button and its logic
		okButton = new JButton("OK");
		okButton.setMnemonic(KeyEvent.VK_0);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory)ageList.getSelectedValue();
				String empCat = (String)empCombo.getSelectedItem();
				String taxId = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();
				String genderSelection = genderGroup.getSelection().getActionCommand();
				//System.out.println(ageCat.getId());
				//System.out.println(ageCat.getText());
				//System.out.println(empCat);

				FormEvent fe = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId, usCitizen, genderSelection);
				if (formListener != null) {
					formListener.formEventOccured(fe);
				}
			}
		});
	}
	
	public void setLayout() {
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/////////// First row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(nameField, gbc);

		/////////// Second row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(occupationLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(occupationField, gbc);

		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Age: "), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(ageList, gbc);
		
		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Employment: "), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(empCombo, gbc);

		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("US Citizen: "), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(citizenCheck, gbc);
		
		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(taxLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(taxField, gbc);
		
		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;
		
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Gender: "), gbc);
		
		/////////// Next row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridy++;
		
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(maleRadio, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(femaleradio, gbc);
		
		/////////// Last row/////////////////////////
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridy++;

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okButton, gbc);

	}
}

class AgeCategory {
	private int id;
	private String text;

	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		return text;
	}

}
