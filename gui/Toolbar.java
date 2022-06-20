package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {
	private JButton saveButton;
	private JButton refreshButton;
	private ToolbarListener toolbarListener;
	private Utils utils;

	public Toolbar() {
		// Get rid of the border if you want the toolbar dragable
		setBorder(BorderFactory.createEtchedBorder());
		utils = new Utils();

		saveButton = new JButton();
		saveButton.setIcon(utils.createIcon("/images/Save16.gif"));
		saveButton.setToolTipText("Save");
		saveButton.addActionListener(this);
		refreshButton = new JButton();
		refreshButton.setToolTipText("Refresh");
		refreshButton.addActionListener(this);
		refreshButton.setIcon(utils.createIcon("/images/Refresh16.gif"));

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(saveButton);
		add(refreshButton);

	}

	public void setToolbarListener(ToolbarListener listener) {
		this.toolbarListener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (toolbarListener != null) {
			if (clicked == saveButton) {
				toolbarListener.saveEventOccured();
			} else if (clicked == refreshButton) {
				toolbarListener.refreshEventOccured();
			}
		}
	}
}