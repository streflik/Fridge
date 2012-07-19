package pl.kti.pk.fridge.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import pl.kti.pk.fridge.IFood;
import pl.kti.pl.fridge.data.*;


public class CreateDialog extends JDialog {

	IFood _item;
	private String _itemType;
	private JTextField _nameField;
	private JTextField _producerField;
	private JTextField _quantityField;
	private JTextField _expirationField;
	private JTextField _addInfoField;
	private JSlider _addInfoSlider;
	private JCheckBox _addInfoCheckbox;
	private JComboBox _addInfoComboBox;


	public CreateDialog(JFrame parentFrame, String itemType) {
		super(parentFrame, "Creating new item", true);

		_itemType = itemType;
		initGUI();
		setResizable(false);
		setSize(400, 300);
		setLocationRelativeTo(parentFrame);
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(5, 5));

		// main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 1));
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// name
		JLabel nameLabel = new JLabel("Name:");
		mainPanel.add(nameLabel);
		_nameField = new JTextField();
		mainPanel.add(_nameField);
		// producer
		JLabel producerLabel = new JLabel("Producer:");
		mainPanel.add(producerLabel);
		_producerField = new JTextField();
		mainPanel.add(_producerField);
		// quantity

		JLabel quantityLabel = new JLabel("Quantity:" );
		mainPanel.add(quantityLabel);
		_quantityField = new JTextField();
		mainPanel.add(_quantityField);
		// expiration
		JLabel expirationLabel = new JLabel("Expiration:");
		mainPanel.add(expirationLabel);
		_expirationField = new JTextField();
		mainPanel.add(_expirationField);

		// type specific additional info
		JLabel addInfoLabel = new JLabel();
		_addInfoField = new JTextField();
		_addInfoSlider = new JSlider(0, 100);
		_addInfoCheckbox = new JCheckBox();
		_addInfoComboBox = new JComboBox();
		if (_itemType == TypeChooser.BUTTER_TYPE) {
			addInfoLabel.setText("Percentage of animal fats:");
			mainPanel.add(addInfoLabel);
			mainPanel.add(_addInfoSlider);
		} else 
			if (_itemType == TypeChooser.CARROTS_TYPE) {
				_addInfoCheckbox.setText("Has leaves:");
				mainPanel.add(_addInfoCheckbox);
			} else 
				if (_itemType == TypeChooser.CHEESE_TYPE) {
					addInfoLabel.setText("Cheese type:");
					mainPanel.add(addInfoLabel);
					_addInfoComboBox.addItem("Swiss");
					_addInfoComboBox.addItem("Cheddar");
					_addInfoComboBox.addItem("Roquefort");
					_addInfoComboBox.addItem("Mozzarella");
					_addInfoComboBox.addItem("Cottage Cheese");
					_addInfoComboBox.addItem("Curd");
					_addInfoComboBox.addItem("Camembert");
					mainPanel.add(_addInfoComboBox);
				} else 
					if (_itemType == TypeChooser.MILK_TYPE) {
						_addInfoCheckbox.setText("Is UHT:");
						mainPanel.add(_addInfoCheckbox);
					} else 
						if (_itemType == TypeChooser.SOFTDRINK_TYPE) {
							addInfoLabel.setText("Flavour:");
							mainPanel.add(addInfoLabel);
							mainPanel.add(_addInfoField);
						} else 
							if (_itemType == TypeChooser.TOMATOES_TYPE) {
								addInfoLabel.setText("Softness:");
								mainPanel.add(addInfoLabel);
								mainPanel.add(_addInfoSlider);
							}


		// buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton okButton = new JButton("OK");
		buttonsPanel.add(okButton);
		JButton cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		JButton expiration = new JButton("Exp");
		buttonsPanel.add(expiration);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createItem();
				setVisible(false);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_item = null;
				setVisible(false);
			}
		});

	}


	public IFood getCreatedItem() {
		return _item;
	}

	private void createItem() {
		try {
			// read common data
			String name = _nameField.getText();
			String producer = _producerField.getText();
			int quantity = Integer.parseInt(_quantityField.getText());
			int expireAfter = Integer.parseInt(_expirationField.getText());
			// read type specific data and create the item
			if (_itemType == TypeChooser.BUTTER_TYPE) {
				int animalFats = _addInfoSlider.getValue();
				_item = new Butter(name, producer, quantity, 100 - animalFats, animalFats, expireAfter);
			} else 
				if (_itemType == TypeChooser.CARROTS_TYPE) {
					boolean leaves = _addInfoCheckbox.isSelected();
					_item = new Carrot(name, producer, quantity,expireAfter, leaves);
				} else 
					if (_itemType == TypeChooser.CHEESE_TYPE) {
						String type = (String) _addInfoComboBox.getSelectedItem();
						_item = new Cheese(name, producer, quantity, expireAfter, type);
					} else 
						if (_itemType == TypeChooser.MILK_TYPE) {
							boolean uht = _addInfoCheckbox.isSelected();
							_item = new Milk(name, producer, quantity, expireAfter, uht);
						} else 
							if (_itemType == TypeChooser.SOFTDRINK_TYPE) {
								String flavour = _addInfoField.getText();
								_item = new SoftDrink(name, producer, quantity, expireAfter, flavour);
							} else 
								if (_itemType == TypeChooser.TOMATOES_TYPE) {
									int softness = _addInfoSlider.getValue();
									_item = new Tomatoes(name, producer, quantity, expireAfter, softness);
								}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			_item = null;
			return;
		}
	}

}