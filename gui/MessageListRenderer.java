package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Message;

public class MessageListRenderer implements ListCellRenderer {

	private JPanel panel;
	private JLabel label;
	private Color selectedColor;
	private Color normalColor;

	public MessageListRenderer() {
		Utils utils = new Utils();
		panel = new JPanel();
		label = new JLabel();

		label.setFont(utils.createFont("/fonts/CrimeWaveBB.ttf").deriveFont(Font.PLAIN, 20));

		selectedColor = new Color(210, 210, 255);
		normalColor = Color.white;

		label.setIcon(utils.createIcon("/images/Information24.gif"));

		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);

	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		Message message = (Message) value;

		label.setText(message.getTitle());

		panel.setBackground(cellHasFocus ? selectedColor : normalColor);
		panel.setOpaque(true);

		return panel;
	}

}
