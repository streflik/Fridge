package pl.kti.pk.fridge.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class TypeChooser extends JDialog {

	public static String BUTTER_TYPE = "Butter";
	public static String CARROTS_TYPE = "Carrots";
	public static String CHEESE_TYPE = "Cheese";
	public static String MILK_TYPE = "Milk";
	public static String SOFTDRINK_TYPE = "Soft drink";
	public static String TOMATOES_TYPE = "Tomatoes";
	
	private String[] _typeNames = { 
		BUTTER_TYPE,
		CARROTS_TYPE,
		CHEESE_TYPE,
		MILK_TYPE,
		SOFTDRINK_TYPE,
		TOMATOES_TYPE
	};
	private JList _typesList;
	protected String _selectedType;
	
	public TypeChooser(JFrame parentFrame) {
		super(parentFrame, "Choose an item type", true);
		
		_typesList = new JList(_typeNames);
		
		initGUI();
		
		setSize(350, 250);
		setLocationRelativeTo(parentFrame);
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(5,0));

		JLabel label = new JLabel("Please choose what type of item you want to create");
		getContentPane().add(label, BorderLayout.NORTH);

		_typesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(_typesList);
		getContentPane().add(listScroller, BorderLayout.CENTER);

		JPanel panel = new JPanel();

		JButton chooseButton = new JButton("Choose");
		JButton cancelButton = new JButton("Cancel");
		panel.add(chooseButton);
		panel.add(cancelButton);

		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object value = _typesList.getSelectedValue();
				if (value == null) {
					System.out.println("No type selected");
				} else {
					String typeName = (String) value;
					System.out.println("Selected: " + typeName);
					_selectedType = typeName;
				}
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_selectedType = null;
				setVisible(false);
			}
		});

		getContentPane().add(panel, BorderLayout.SOUTH);
	}
	
	public String getSelectedTypeName() {
		return _selectedType;
	}
	
}
