package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;

public class MainFrame extends JFrame {

	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fc;
	private Controller controller;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;
	private MessagePanel messagePanel;

	public MainFrame() {
		super("Hello world Database");
		
		//try {
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		//	UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		//} catch (Exception e2) {
			// TODO Auto-generated catch block
		//	e2.printStackTrace();
		//	System.out.println("Can't set look and feel.");
		//} 

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		setLayout(new BorderLayout());

		fc = new JFileChooser();
		PersonFileFilter pff = new PersonFileFilter();
		fc.addChoosableFileFilter(pff);
		fc.setFileFilter(pff);

		toolbar = new Toolbar();
		formPanel = new FormPanel();
		setJMenuBar(createMenuBar());
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog(this);
		tabbedPane = new JTabbedPane();
		messagePanel = new MessagePanel(this);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabbedPane);

		splitPane.setOneTouchExpandable(true);

		tabbedPane.addTab("Person Database", tablePanel);
		tabbedPane.addTab("Messages", messagePanel);

		prefs = Preferences.userRoot().node("db");

		controller = new Controller();

		tablePanel.setData(controller.getPeople());
		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				System.out.println(row);
				controller.removePerson(row);
			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int tabbedIndex = tabbedPane.getSelectedIndex();

				if (tabbedIndex == 1) {
					messagePanel.refresh();
				}
			}
		});

		prefsDialog.setPrefsListener(new PrefsListener() {
			@Override
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
				System.out.println(user + " : " + password);

				try {
					controller.configure(port, user, password);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to re-connect", "Re-connection Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);

		prefsDialog.setDefaults(user, password, port);

		try {
			controller.configure(port, user, password);
		} catch (Exception e1) {
			System.err.println("Can't connect to database");
		}

		toolbar.setToolbarListener(new ToolbarListener() {
			@Override
			public void saveEventOccured() {
				System.out.println("Save event occured");
				connect();
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Can not save to database", "Database Saving Problem",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			public void refreshEventOccured() {
				refresh();
			}

		});

		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccured(FormEvent fe) {

				controller.addPerson(fe);
				tablePanel.refresh();
			}
		});

		add(toolbar, BorderLayout.PAGE_START);
		add(splitPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.out.println("Window is closing!");
				dispose();
				System.gc();
			}
		});

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setVisible(true);
	}

	private void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Can not connect to database", "Database Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Exoprt Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(importDataItem);
		fileMenu.add(exportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		prefsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});

		showFormItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

				if (menuItem.isSelected()) {
					splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
				}
				formPanel.setVisible(menuItem.isSelected());
			}
		});

		// set mnemonics
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fc.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fc.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// String text = JOptionPane.showInputDialog(MainFrame.this, "Enter your user
				// name", "Enter User Name",
				// JOptionPane.OK_OPTION|JOptionPane.QUESTION_MESSAGE);
				// System.out.println(text);

				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] windowListeners = getWindowListeners();

					for (WindowListener listener : windowListeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});

		return menuBar;
	}

	private void refresh() {
		try {
			controller.connect();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database", "Database connection Failure",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			controller.load();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Can not load from database", "Database Loading Problem",
					JOptionPane.ERROR_MESSAGE);
		}
		tablePanel.refresh();
	}
}
