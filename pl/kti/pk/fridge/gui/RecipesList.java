package pl.kti.pk.fridge.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.table.*;

import pl.kti.pk.fridge.IFood;
import pl.kti.pk.fridge.Recipe;

public class RecipesList extends JFrame{

	static RecipesList _frame;
	protected RecipesTable _recipesTable; 
	private FridgeTableModel _fridgeTable;
	private JTable recipesTable;
	JButton consumeBtn;

	public RecipesList(FridgeFrame frame,FridgeTableModel fridgeTable) throws Exception {

		super("Recipes");

		_fridgeTable=fridgeTable;
		_frame=this;
		setResizable(true);
		setSize(600, 550);

		_recipesTable = new RecipesTable(_fridgeTable.getAvailableRecipes(),_fridgeTable.getAllRecipes());
		recipesTable = new JTable(_recipesTable){
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
		initGUI();

		setLocationRelativeTo(frame);


	}

	private void initGUI() {

		recipesTable.setAutoCreateRowSorter(true);
		getContentPane().setLayout(new BorderLayout(3, 3));
		JScrollPane listScroller = new JScrollPane();
		getContentPane().add(listScroller,BorderLayout.CENTER);

		listScroller.setViewportView(recipesTable);
		recipesTable.setRowHeight(100);
		// Disable auto resizing
		recipesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		// Set column width
		TableColumn col = recipesTable.getColumnModel().getColumn(1);
		col.setMinWidth(90);


		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(5, 1));
		getContentPane().add(actionPanel,BorderLayout.SOUTH);


		JButton create = new JButton("Create New Recipe");
		actionPanel.add(create);

		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					CreateRecipeDialog createDlg = new CreateRecipeDialog(null);
					createDlg.setVisible(true);
					Recipe przepis=createDlg.getCreatedItem();
					if (przepis != null) {
						_recipesTable.put(przepis);
						_recipesTable.setAvailableRecipes(_fridgeTable.getAvailableRecipes());
					}
				}catch(Exception x){}
			}
		});


		consumeBtn = new JButton("Consume Selected");
		actionPanel.add(consumeBtn);
		consumeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					actionConsumeSelected();
				}catch(Exception x){}

			}
		});


		JButton allRecipes = new JButton("Show All Recipes");
		actionPanel.add(allRecipes);
		allRecipes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{					
					consumeBtn.setEnabled(false);
					_recipesTable.setAvailableRecipes(_fridgeTable.getAllRecipes());	
				}catch(Exception x){}
			}
		});

		JButton availableRecipes = new JButton("Show Available Recipes");
		actionPanel.add(availableRecipes);
		availableRecipes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					consumeBtn.setEnabled(true);
					_recipesTable.setAvailableRecipes(_fridgeTable.getAvailableRecipes());	
				}catch(Exception x){}
			}
		});



		JButton closeBtn = new JButton("Close Recipes");
		actionPanel.add(closeBtn);
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_frame.hide();
			}
		});

	}

	public void actionConsumeSelected() throws Exception{
		Recipe przepis=_recipesTable.get(recipesTable.getSelectedRow());
		for (IFood item: przepis.getIngridients()){
			_fridgeTable.consume(item.getClass(), item.howMuchLeft());
		}
		_recipesTable.setAvailableRecipes(_fridgeTable.getAvailableRecipes());

	}

}
