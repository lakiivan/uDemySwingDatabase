package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {

	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField userField;
	private JPasswordField passField;
	private PrefsListener prefsListener;

	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);

		userField = new JTextField(10);
		passField = new JPasswordField(10);
		passField.setEchoChar('*');

		layoutControls();

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int port = (Integer) portSpinner.getValue();

				String user = userField.getText();
				char[] password = passField.getPassword();

				if (prefsListener != null) {
					prefsListener.preferencesSet(user, new String(password), port);
				}

				System.out.println(user + " : " + new String(password));
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setSize(300, 230);
		setLocationRelativeTo(parent);

	}

	public void setPrefsListener(PrefsListener prefsListener) {
		this.prefsListener = prefsListener;
	}

	public void setDefaults(String user, String password, int port) {
		userField.setText(user);
		passField.setText(password);
		portSpinner.setValue(port);
	}

	private void layoutControls() {

		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		// buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		controlsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);

		//////////////// first row/////////////////////

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = rightPadding;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		controlsPanel.add(new JLabel("User: "), gbc);

		gbc.gridx++;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = noPadding;
		controlsPanel.add(userField, gbc);

		//////////////// next row/////////////////////
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = rightPadding;
		gbc.fill = GridBagConstraints.NONE;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		controlsPanel.add(new JLabel("Password: "), gbc);

		gbc.gridx++;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = noPadding;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		controlsPanel.add(passField, gbc);

		//////////////// next row/////////////////////
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = rightPadding;
		controlsPanel.add(new JLabel("Port: "), gbc);

		gbc.gridx++;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = noPadding;
		controlsPanel.add(portSpinner, gbc);

		//////////////// buttons panel/////////////////////
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(okButton);
		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);
		// add sub panels to dialog
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

	}

}
