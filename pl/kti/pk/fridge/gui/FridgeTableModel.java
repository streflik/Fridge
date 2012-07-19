package pl.kti.pk.fridge.gui;
import pl.kti.pk.fridge.*;
import java.util.*; 
import javax.swing.table.AbstractTableModel;
import javax.swing.*;

public class FridgeTableModel extends AbstractTableModel {
	private String[] columnNames = { "Type", "Name", "Producer", "Quantity", "Valid", "Additional info" };
	protected Fridge _fridge ;
	public FridgeTableModel(Fridge fridge) {
		_fridge = fridge;
	}

	public int getRowCount() {
		return _fridge.getItemCount();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}


	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			IFood item = _fridge.get(rowIndex);
			if (columnIndex == 0) {
				String className = item.getClass().getName();
				return className.substring(className.lastIndexOf('.')+1).toUpperCase();
			}
			if (columnIndex == 1) {
				return item.getName();
			}
			if (columnIndex == 2) {
				return item.getProducer();
			}
			if (columnIndex == 3) {

				return item.howMuchLeft() +" "+ item.getUnits();
			}
			if (columnIndex == 4) {
				return !item.isExpired();
			}
			if (columnIndex == 5) {
				return "<HTML> "+item.getAdditionalInfo()+" </HTML>";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Item not found!", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public void removeExpired() {
		_fridge.removeExpired();
		fireTableDataChanged();        // updates GUI after data modifications
	}

	public void removeAll() {
		_fridge.removeAll();
		fireTableDataChanged();        // updates GUI after data modifications
	}
	public void remove(int index) {
		_fridge.remove(index);
		fireTableDataChanged();
	}
	public void consume(int index, int amount_to_consume) {
		try{
			AFood item=(AFood)_fridge.get(index);
			item.consume(amount_to_consume);
			_fridge.put(item);
		}catch(Exception x){}
		fireTableDataChanged();
	}

	public void consume(Class name, int amount_to_consume) {
		try{
			AFood item=(AFood)_fridge.get(name);
			item.consume(amount_to_consume);
			_fridge.put(item);
		}catch(Exception x){}
		fireTableDataChanged();
	}

	public void put(IFood item)  {
		try{
			_fridge.put(item);
		}catch(Exception x){}
		fireTableDataChanged();
	}
	public Fridge getFridge(){
		return _fridge;
	}


	public Collection<Recipe> getAllRecipes() throws Exception{
		return _fridge.readRecipes();
	}

	public Collection<Recipe> getAvailableRecipes() throws Exception{
		return _fridge.getRecipe();
	}
}
