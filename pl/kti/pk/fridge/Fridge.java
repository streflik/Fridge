package pl.kti.pk.fridge;

import java.io.*;
import java.util.*; 

import javax.swing.JOptionPane;

import pl.kti.pl.fridge.data.*;
public class Fridge {

	public Fridge() throws Exception{
		_items = new HashSet<IFood>();
		readRecipes();
	}

	private static int CAPACITY = 50;

	private Collection<IFood> _items;

	private Collection<Recipe> przepisy = new HashSet<Recipe>();
	private Collection<Recipe> przepisyMozliwe = new HashSet<Recipe>();
	public void put(IFood nowy) throws Exception
	{
		if (_items.size()>CAPACITY){
			throw new Exception("Fridge OVERLOADED! Cannot add new food!");
		}

		_items.add(nowy);
	}


	public IFood get(int index) throws Exception {
		// iterate through the _items collection ...
		int x = 0;
		for (IFood item : _items) {

			if (x == index) {
				return item;
			}
			x++;
		}
		throw new Exception("Item '"+index+"' not found!");
	}

	public Collection<Recipe> getRecipe()throws Exception{
		przepisy =readRecipes();
		przepisyMozliwe.clear();
		for(Recipe przepis : przepisy){
			int x=0;
			for(IFood ingridients : przepis.getIngridients()){
				for(IFood item : _items){

					if(ingridients.getClass()==item.getClass()&& ingridients.howMuchLeft()<=item.howMuchLeft()){
						if (ingridients.getClass().equals(Cheese.class)){
							if (ingridients.getAdditionalInfo().equals(item.getAdditionalInfo())){
								x++;
								break;
							}
						}else{
							x++;
							break;
						}
					}
				}
			}
			int rozmiar=przepis.getIngridients().size();
			if (x==rozmiar){
				przepisyMozliwe.add(przepis);
			}
		}
		return przepisyMozliwe;
	}

	public Collection<Recipe> readRecipes() throws Exception{
		przepisy.clear();
		File file = new File("recipes.dat");
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "File with recipes doesn't exist!", "ERROR!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		IFood item= null;
		String line1;
		Recipe przepis;
		String line;
		String name;
		while (!(name=breader.readLine()).equals("$koniec$")) {

			String description = breader.readLine();
			przepis=new Recipe(name,description);
			while (!(line1=breader.readLine()).equals("")) {
				int quantity = Integer.parseInt(line1);
				line=breader.readLine();


				if (line.equals("Soft Drink")) {
					item=new SoftDrink("Soft Drink"," ",50,quantity, " ");
				} else if (line.equals("Milk")) {
					item=new Milk("Milk"," ",quantity, 50,true);
				} else if (line.equals("Carrot")){
					item=new Carrot("Carrot"," f",quantity,34, true);
				}else if (line.equals("Butter")){
					item=new Butter("Butter"," ",quantity, 50, 50, 5);

				}else if (line.equals("Tomatoes")){
					item=new Tomatoes("Tomatoes"," ",quantity, 50, 50);

				}else if (line.equals("Cheese")){
					item=new Cheese("Cheese"," ",quantity, 50,breader.readLine());
				}
				else {
					System.err.println("Unknown food type: " + line);
				}
				przepis.putIngridient(item);
			}
			przepisy.add(przepis);


		} 
		breader.close();
		return przepisy;
	}

	public IFood get(Class name) throws Exception{
		for (IFood item : _items) {

			if (item.getClass()==name) {
				_items.remove(item);
				return item;
			}

		}
		throw new Exception("Item '"+name+"' not found!");
	} 

	public IFood get(String name) throws Exception{
		for (IFood item : _items) {

			if (item.getName().equals(name)) {
				_items.remove(item);
				return item;
			}

		}
		throw new Exception("Item '"+name+"' not found!");
	} 

	public int getItemCount() {
		return _items.size();
	}

	public void removeAll() {
		_items.clear();
	}

	public void removeExpired() {
		Collection<IFood> items_to_remove = new ArrayList<IFood>();
		// find all expired items
		for (IFood item : _items) {
			if (item.isExpired()) {
				items_to_remove.add(item);
			}
		}
		// remove all found items
		for (IFood item : items_to_remove) {
			_items.remove(item);
		}
	}
	public void remove(int index) {
		int x = 0;
		for (IFood item : _items) {

			if (x == index) {
				_items.remove(item);
				break;
			}
			x++;
		}
	}

	public void serializeProducts(BufferedWriter bwriter) throws IOException{
		bwriter.write("FRIDGEv1.0\n");
		for (IFood item: _items){
			item.serialize(bwriter);
		}

	}

	public boolean deserializeProducts(BufferedReader breader) throws Exception {
		_items.clear();
		String line;
		if (breader.readLine().equals("FRIDGEv1.0")){
			while ((line = breader.readLine()) != null) {
				String name = breader.readLine();
				String producer = breader.readLine();
				int quantity = Integer.parseInt(breader.readLine());
				int expiredAfter = Integer.parseInt(breader.readLine());
				if (line.equals("SOFTDRINK")) {
					String flavour = breader.readLine();
					SoftDrink item = new SoftDrink(name, producer, quantity, expiredAfter, flavour);
					_items.add(item);
				} else if (line.equals("MILK")) {
					Boolean uht = Boolean.parseBoolean(breader.readLine());
					Milk item = new Milk(name, producer, quantity, expiredAfter, uht);
					_items.add(item);
				} else if (line.equals("CARROT")){
					Boolean leaves = Boolean.parseBoolean(breader.readLine());
					Carrot item = new Carrot(name, producer, quantity, expiredAfter, leaves);
					_items.add(item);
				}else if (line.equals("BUTTER")){
					int animalFat = Integer.parseInt(breader.readLine());
					int vegetableFat = Integer.parseInt(breader.readLine());
					Butter item = new Butter(name, producer, quantity, expiredAfter, animalFat, vegetableFat);
					_items.add(item);

				}else if (line.equals("TOMATOES")){
					int softness = Integer.parseInt(breader.readLine());
					Tomatoes item = new Tomatoes(name, producer, quantity, expiredAfter, softness);
					_items.add(item);

				}else if (line.equals("CHEESE")){
					String type = breader.readLine();
					Cheese item = new Cheese(name, producer, quantity, expiredAfter, type);
					_items.add(item);
				}
				else {
					System.err.println("Unknown food type: " + line);
				}	
			}
		}else			
			return false;

		return true;
	}

	public Collection<IFood>getItems(){
		return _items;
	}

}
