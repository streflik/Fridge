package pl.kti.pk.fridge.gui;
import pl.kti.pk.fridge.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import java.io.*;

public class FridgeFrame extends JFrame {
	protected FridgeTableModel _fridgeTableModel;
	protected JTable _fridgeTable;


	static FridgeFrame _frame;
	public FridgeFrame(Fridge fridge) {

		super("My Fridge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_fridgeTableModel = new FridgeTableModel(fridge);
		_frame=this;
		setSize(750, 500);
		initGUI();
		//pack();
		setLocationRelativeTo(null);
	}

	public static void refresh() {
		if (_frame != null) {
			_frame.repaint();
		}
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(5,5));

		// setting main GUI components
		JScrollPane listScroller = new JScrollPane();
		getContentPane().add(listScroller,BorderLayout.CENTER);
		JTable fridgeTable = new JTable(_fridgeTableModel){
			public Component prepareRenderer(TableCellRenderer renderer,
					int rowIndex, int vColIndex) {
				Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);

				if (rowIndex % 2 == 0) {
					if (isCellSelected(rowIndex, vColIndex)){
						c.setBackground(Color.getHSBColor(149, 50, 100));
					}else
						c.setBackground(Color.LIGHT_GRAY);
				} 
				else if (isCellSelected(rowIndex, vColIndex)){
					c.setBackground(Color.getHSBColor(149, 50, 100));
				}
				else{
					// If not shaded, match the table's background
					c.setBackground(getBackground());
				}
				return c;
			}
		};
		_fridgeTable = fridgeTable;
		listScroller.setViewportView(fridgeTable);
		_fridgeTable.setAutoCreateRowSorter(true);
		_fridgeTable.setRowHeight(40);
		_fridgeTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		// Set the first visible column to 100 pixels wide
		TableColumn col = _fridgeTable.getColumnModel().getColumn(0);
		col.setMaxWidth(80);
		col = _fridgeTable.getColumnModel().getColumn(3);
		col.setMaxWidth(75);
		col = _fridgeTable.getColumnModel().getColumn(4);
		col.setMaxWidth(50);

		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(5, 2));
		getContentPane().add(actionPanel,BorderLayout.SOUTH);


		JButton createBtn = new JButton("Create");
		actionPanel.add(createBtn);
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent	 e) {
				TypeChooser typeChooser = new TypeChooser(null);
				typeChooser.setVisible(true);
				String selectedType = typeChooser.getSelectedTypeName();
				if (selectedType == null) {          // will be null if user click Cancel
					return;
				}	
				// display product creation dialog and create product
				// a) ask for the details ...
				CreateDialog createDlg = new CreateDialog(_frame, selectedType);
				createDlg.setVisible(true);

				// b) ... and get the new item
				IFood item = createDlg.getCreatedItem();
				if (item != null) {
					_fridgeTableModel.put(item);
				}
			}
		});

		JButton consumeBtn = new JButton("Consume");
		actionPanel.add(consumeBtn);
		consumeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent	 e) {
				int index = _fridgeTable.getSelectedRow();        // which row is selected
				if (index >= 0) {
					String userResponse = JOptionPane.showInputDialog(_fridgeTable, "Provide the amount to consume");

					if (!userResponse.equals("")){
						int amount_to_consume = Integer.parseInt(userResponse);

						if (amount_to_consume > 0){
							_fridgeTableModel.consume(index, amount_to_consume);
						} else  {
							// error dialog
							JOptionPane.showMessageDialog(_frame, "You cannot consume less than zero");

						}
					}
					else {
						JOptionPane.showMessageDialog(_frame, "Provide the amount !");
					}
				}
			}
		});

		JButton removeBtn = new JButton("Remove");
		actionPanel.add(removeBtn);
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				int index = _fridgeTable.getSelectedRow();        // which row is selected
				if (index >= 0) {
					_fridgeTableModel.remove(index);
				}
			}

		});
		JButton removeExp = new JButton("Remove expired");
		actionPanel.add(removeExp);
		removeExp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				_fridgeTableModel.removeExpired();
			}
		});

		JButton removeAll = new JButton("Remove all");
		actionPanel.add(removeAll);
		removeAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				_fridgeTableModel.removeAll();
			}
		});
		JButton save = new JButton("Save");
		actionPanel.add(save);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				actionSaveProducts();
			}
		});

		JButton load = new JButton("Load");
		actionPanel.add(load);
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				actionLoadProducts();
			}
		});

		JButton recipe = new JButton("Available recipes");
		actionPanel.add(recipe);
		recipe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent	 e) {
				actionShowRecipes();
			}
		});



		JButton closeBtn = new JButton("Close fridge");
		closeBtn.setFont(new Font(closeBtn.getFont().getFamily(), Font.BOLD, 16));
		closeBtn.setForeground(Color.RED);
		actionPanel.add(closeBtn);

		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	private void actionShowRecipes(){
		try{
			RecipesList frame = new RecipesList(_frame,_fridgeTableModel);
			frame.setVisible(true);
		}catch(Exception x){};
	}


	public void actionSaveProducts() {
		try{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showDialog(_frame, "Choose file") == JFileChooser.CANCEL_OPTION) {
				fileChooser.setVisible(false);
				return;
			}
			File file = fileChooser.getSelectedFile();
			fileChooser.setVisible(false);
			FileWriter fwriter = new FileWriter(file);
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			Fridge fridge = _fridgeTableModel.getFridge();
			fridge.serializeProducts(bwriter);
			bwriter.close();
			JOptionPane.showMessageDialog(_frame, "Fridge was successfuly saved");
		}
		catch(Exception x){
			x.printStackTrace();
		}
	}

	public void actionLoadProducts(){
		try{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showDialog(_frame, "Choose file") == JFileChooser.CANCEL_OPTION) {
				fileChooser.setVisible(false);
				return;
			}
			File file = fileChooser.getSelectedFile();
			if (!file.exists()) {
				JOptionPane.showMessageDialog(_frame, "File does not exist!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			fileChooser.setVisible(false);
			FileReader freader = new FileReader(file);
			BufferedReader breader = new BufferedReader(freader);
			Fridge fridge = _fridgeTableModel.getFridge();
			if (fridge.deserializeProducts(breader)){
				JOptionPane.showMessageDialog(_frame, "Fridge was successfuly loaded");
			}else
				JOptionPane.showMessageDialog(null, "This file is not correct 'Fridge' file");
			_fridgeTableModel.fireTableDataChanged();
			breader.close();

		}catch(Exception x){
			x.printStackTrace();
		}
	}

}
