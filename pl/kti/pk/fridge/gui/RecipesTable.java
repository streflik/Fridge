package pl.kti.pk.fridge.gui;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import pl.kti.pk.fridge.IFood;
import pl.kti.pk.fridge.Recipe;
import pl.kti.pl.fridge.data.*;
import java.util.*;
import java.io.*;


public class RecipesTable extends AbstractTableModel {
	private Collection<Recipe> _przepisyMozliwe= new HashSet<Recipe>();
	private Collection<Recipe> _przepisyAll=new HashSet<Recipe>();
	private String ingridientList;
	private String[] columnNames = { "Name", "Description","Ingridients"};



	public RecipesTable(Collection<Recipe> przepisy,Collection<Recipe> przepisyAll) throws Exception {
		_przepisyMozliwe=przepisy;
		_przepisyAll=przepisyAll;

	}

	public int getRowCount() {
		return _przepisyMozliwe.size();

	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			ingridientList="<HTML> ";
			Recipe przepis = get(rowIndex);
			if (columnIndex == 0) {

				return przepis.get_recipeName();
			}
			if (columnIndex == 1) {
				return "<HTML> "+ przepis.get_description()+" </HTML>";
			}
			if (columnIndex == 2) {
				for (IFood ingridient : przepis.getIngridients()){
					ingridientList+=ingridient.getName()+" : "+ingridient.howMuchLeft()+" "+ingridient.getUnits();
					if (ingridient instanceof Cheese){
						ingridientList+=" "+ingridient.getAdditionalInfo()+" <br/>";
					}else
						ingridientList+=" <br/>";

				}
				ingridientList+=" </HTML>";
				return ingridientList;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Item not found!", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	public void put(Recipe item)  {
		try{

			_przepisyAll.add(item);
			File file = new File("recipes.dat");
			FileWriter fwriter = new FileWriter(file);
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			for (Recipe recipe : _przepisyAll){
				bwriter.write(recipe.get_recipeName() + "\n");
				bwriter.write(recipe.get_description() + "\n");
				for(IFood ingridient : recipe.getIngridients()){
					bwriter.write(ingridient.howMuchLeft() + "\n");
					bwriter.write(ingridient.getName() + "\n");
					if (ingridient.getName().equals("Cheese")){
						Cheese ingr=(Cheese) ingridient;
						bwriter.write(ingr.getType()+"\n");
					}

				}
				bwriter.write("\n");
			}
			bwriter.write("$koniec$");
			bwriter.close();
		}catch(Exception x){}
		fireTableDataChanged();
	}


	public Recipe get(int index)throws Exception{
		// iterate through the _items collection ...
		int x = 0;
		for (Recipe item : _przepisyMozliwe) {

			if (x == index) {
				return item;
			}
			x++;
		}

		throw new Exception("Item '"+index+"' not found!");

	}

	public void setAvailableRecipes(Collection<Recipe> recipes){
		_przepisyMozliwe=recipes;
		fireTableDataChanged();
	}
}
