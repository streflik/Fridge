package pl.kti.pk.fridge.gui;
import pl.kti.pk.fridge.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField ;
import javax.swing.JComboBox;

import pl.kti.pk.fridge.IFood;
import pl.kti.pl.fridge.data.Butter;
import pl.kti.pl.fridge.data.Carrot;
import pl.kti.pl.fridge.data.Cheese;
import pl.kti.pl.fridge.data.Milk;
import pl.kti.pl.fridge.data.SoftDrink;
import pl.kti.pl.fridge.data.Tomatoes;

public class CreateRecipeDialog extends JDialog implements PropertyChangeListener {

	Recipe _recipe;
	private JTextArea _descriptionField;
	private JTextField _nameField;
	private JTextField _butterField;
	private JTextField _milkField;
	private JTextField _carrotField;
	private JTextField _softdrinkField;
	private JTextField _tomatoesField;
	private JFormattedTextField _cheeseField;
	public JComboBox _addInfoComboBox;
	public JLabel addInfoLabel;
	//private Collection<Recipe> _recipes;
	private Collection<IFood> _ingridients = new HashSet<IFood>();



	public CreateRecipeDialog(JFrame parentFrame) {
		super(parentFrame, "Creating new Recipe", true);
		initGUI();
		setResizable(false);
		setSize(400, 400);
		setLocationRelativeTo(parentFrame);
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(5, 5));

		// main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 1));
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		JLabel nameLabel = new JLabel("Name:" );
		mainPanel.add(nameLabel);
		_nameField = new JTextField();
		mainPanel.add(_nameField);

		JLabel descriptionLabel = new JLabel("Description:" );
		mainPanel.add(descriptionLabel);
		_descriptionField = new JTextArea();
		_descriptionField.setRows(3);
		_descriptionField.setLineWrap(true);
		mainPanel.add(_descriptionField);

		JLabel butterLabel = new JLabel("Butter:" );
		mainPanel.add(butterLabel);
		_butterField = new JTextField("0");
		mainPanel.add(_butterField);

		JLabel carrotLabel = new JLabel("Carrot:" );
		mainPanel.add(carrotLabel);
		_carrotField = new JTextField("0");
		mainPanel.add(_carrotField);

		JLabel cheeseLabel = new JLabel("Cheese:" );
		mainPanel.add(cheeseLabel);
		_cheeseField = new JFormattedTextField("0");
		mainPanel.add(_cheeseField);
		_cheeseField.addPropertyChangeListener("value", this);



		addInfoLabel=new JLabel("Cheese type:");
		mainPanel.add(addInfoLabel);
		_addInfoComboBox = new JComboBox();
		_addInfoComboBox.addItem("Swiss");
		_addInfoComboBox.addItem("Cheddar");
		_addInfoComboBox.addItem("Roquefort");
		_addInfoComboBox.addItem("Mozzarella");
		_addInfoComboBox.addItem("Cottage Cheese");
		_addInfoComboBox.addItem("Curd");
		_addInfoComboBox.addItem("Camembert");

		mainPanel.add(_addInfoComboBox);
		addInfoLabel.setVisible(false);
		_addInfoComboBox.setVisible(false);

		JLabel milkLabel = new JLabel("Milk:" );
		mainPanel.add(milkLabel);
		_milkField = new JTextField("0");
		mainPanel.add(_milkField);

		JLabel softdrinkLabel = new JLabel("Soft Drink:" );
		mainPanel.add(softdrinkLabel);
		_softdrinkField = new JTextField("0");
		mainPanel.add(_softdrinkField);

		JLabel tomatoesLabel = new JLabel("Tomatoes:" );
		mainPanel.add(tomatoesLabel);
		_tomatoesField = new JTextField("0");
		mainPanel.add(_tomatoesField);

		// buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton okButton = new JButton("OK");
		buttonsPanel.add(okButton);
		JButton cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createItem();
				setVisible(false);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_recipe = null;
				setVisible(false);
			}
		});
	}

	public void propertyChange(PropertyChangeEvent e) {

		addInfoLabel.setVisible(true);
		_addInfoComboBox.setVisible(true);
	}

	public Recipe getCreatedItem() {
		return _recipe;
	}

	private void createItem() {
		try {
			int butter = Integer.parseInt(_butterField.getText());
			int carrot = Integer.parseInt(_carrotField.getText());
			int milk = Integer.parseInt(_milkField.getText());
			int cheese = Integer.parseInt(_cheeseField.getText());
			int softdrink = Integer.parseInt(_softdrinkField.getText());
			int tomatoes = Integer.parseInt(_tomatoesField.getText());
			if (butter!=0){

				_ingridients.add(new Butter("Butter"," ",butter, 50, 50, 5));
			}
			if (carrot!=0){
				_ingridients.add(new Carrot("Carrot"," ",carrot,34, true));
			}
			if (cheese!=0){
				_ingridients.add(new Cheese("Cheese"," ",cheese, 50,(String) _addInfoComboBox.getSelectedItem()));
			}
			if (milk!=0){
				_ingridients.add(new Milk("Milk"," ",milk, 50,true));
			}
			if (softdrink!=0){
				_ingridients.add(new SoftDrink("Soft Drink"," ",50,softdrink, " "));
			}
			if (tomatoes!=0){
				_ingridients.add(new Tomatoes("Tomatoes"," ",tomatoes, 50, 50));
			}
			_recipe=new Recipe(_nameField.getText(),_descriptionField.getText(),_ingridients);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			_recipe = null;
			return;
		}
	}

}

