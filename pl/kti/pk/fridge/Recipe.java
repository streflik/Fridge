package pl.kti.pk.fridge;

import java.util.Collection;
import java.util.HashSet;

public class Recipe {
	protected String _recipeName;
	protected String _description;
	private Collection<IFood> _ingridients;
	
	
	public Recipe(String name, String description,
			Collection<IFood> ingridients) {
		 _ingridients = new HashSet<IFood>();
		_recipeName = name;
		_description = description;
		_ingridients = ingridients;
	}
	public Recipe(String name, String description) {
		 _ingridients = new HashSet<IFood>();
		_recipeName = name;
		_description = description;
	
	}
	
	public Collection<IFood> getIngridients(){
		return _ingridients;
	}
	
	public void putIngridient(IFood item){
		_ingridients.add(item);
	}

	public String get_recipeName() {
		return _recipeName;
	}

	public void set_recipeName(String name) {
		_recipeName = name;
	}

	public String get_description() {
		return _description;
	}

	public void set_description(String _description) {
		this._description = _description;
	}
		
}
