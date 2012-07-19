package pl.kti.pk.fridge.tui;
import java.io.*;
import java.util.*;
import pl.kti.pk.fridge.*;
import pl.kti.pl.fridge.data.*;

public class FridgeController {

	protected Fridge _fridge ;
	protected boolean _done;


	public FridgeController(Fridge fridge) {
		super();
		_fridge = fridge;
		this._done = false;
	}


	public void run() throws Exception {
		System.out.println();
		System.out.println("Welcome to the Interactive Fridge!");
		System.out.println();
		for(int i=1;i<=1;i++){
			_fridge.put(new SoftDrink("cola","cola",34,5,"cherry"));
		}
		_fridge.put(new Carrot("marchewka","ktos",34,4,true));
		_fridge.put(new Butter("maslo","cola",300,5,34,65));
		_fridge.put(new Tomatoes("marchew","coladd",34,34,12));
		// main action
		while (!_done) {
			runMainMenu();
		}

		System.out.println();
		System.out.println("Interactive Fridge - The End!");
		System.out.println();
	}


	private void runMainMenu() throws Exception {
		// display the menu
		System.out.println();
		System.out.println("=======================================================");
		System.out.println("What do you want to do?");
		System.out.println("a) Display fridge contents");
		System.out.println("b) Create new item and put it to the fridge");
		System.out.println("c) Delete item from the fridge");
		System.out.println("d) Consume something");
		System.out.println("e) Show avaiable Recipies");
		System.out.println("x) Exit Interactive Fridge");
		System.out.println("-------------------------------------------------------");
		char option = readChar("Choose an option: ");
		System.out.println("-------------------------------------------------------");
		// act accordingly
		switch (option) {
		case 'a' : 
			// display fridge contents
			displayFridgeContents();
			break;
		case 'b' : 
			// create new item ...
			IFood item = actionCreateItem();
			// ... and put it to the fridge
			_fridge.put(item);
			System.out.println("... and placed in the fridge.");
			break;
		case 'c' : 
			//delete product
			actionDeleteItem();
			break;
		case 'd' : 
			// consume selected item
			actionConsumeItem();
			break;
		case 'e' : 
			// consume selected item
			actionShowRecipies();
			break;
		case 'x' : 
			// close the application
			_done = true;
			break;
		default : 
			System.err.println("WARNING: Unknown option!");
		}
		System.out.println("=======================================================");
	}

	private void actionShowRecipies() throws Exception{
		//Collection<Recipe> przepisy= _fridge.getRecipe();
		for(Recipe przepis: _fridge.getRecipe()){
			System.out.println("Nazwa potrawy: "+przepis.get_recipeName());
			System.out.println("Opis: "+przepis.get_description());
		}
	}

	private char readChar(String prompt) {
		char userResponse = ' ';

		// display the prompt
		System.out.print(prompt);

		// wait for user input and read the character provided by the user
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			String tmp  = input.readLine();
			userResponse = (char) tmp.charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return what user has typed
		return userResponse;
	}


	private void displayFridgeContents() {
		System.out.println("Your fridge contains "+_fridge.getItems().size()+" items:");
		int counter = 1;
		for (Iterator i = _fridge.getItems().iterator(); i.hasNext();) {
			IFood item = (IFood) i.next();
			System.out.println("      "+ counter +")Name: "+ item.getName() + "\n\tProducer: " + item.getProducer() + "\n\tQuantity: " + item.howMuchLeft() + "\n\tInfo:\n\t" + item.getAdditionalInfo()+" [" + (item.isExpired()?"INVALID":"valid") + "]");
			counter++;
		}
	}

	private void actionConsumeItem(){
		try {
			displayFridgeContents();
			String name = readString("Please provide name of the item you want to consume: ");
			if (name == null) {
				System.err.println("WARNING: Empty answer - exiting action!");
				return;
			}
			else{
				IFood item=_fridge.get(name);
				int _howMuch = readNumber("Please provide how much you want to consume: ");
				item.consume(_howMuch);
				System.out.println("Now you have " + item.howMuchLeft() + " units of " + item.getName() + " left.");
				_fridge.put(item);

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}	


	private IFood actionCreateItem(){
		try{
			String foodType = readString("Please provide food type you want to create: ");	
			foodType=foodType.toLowerCase();
			if (foodType.equals("softdrink")) {
				// get Hat information
				System.out.println("   Creating SoftDrink:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				String flavour = readString("     Provide flavour: ");
				System.out.println("-------------------------------------------------------");
				SoftDrink newSoftDrink = new SoftDrink(name, producer, quantity,currency_period, flavour);
				System.out.println("New SoftDrink was created: "+ newSoftDrink.getName() + ", " + newSoftDrink.getProducer() + " : " + newSoftDrink.howMuchLeft() +" "+newSoftDrink.getFlavour() +" [" + (newSoftDrink.isExpired()?"INVALID":"valid") + "]"+" ..." );
				return newSoftDrink;
			} 
			else if(foodType.equals("butter")){
				System.out.println("   Creating Butter:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				int animalFats = readNumber("     Provide animal fats: ");
				int vegetableFats = readNumber("     Provide vegetable fats: ");
				System.out.println("-------------------------------------------------------");
				Butter newButter = new Butter(name, producer, quantity,currency_period, animalFats, vegetableFats);
				System.out.println("New Butter was created: "+ newButter.getName() + ", " + newButter.getProducer() + " : " + newButter.getVegetableFats() +" "+newButter.getAnimalFats() +" [" + (newButter.isExpired()?"INVALID":"valid") + "]"+" ..." );
				return newButter;
			}
			else if(foodType.equals("carrot")){
				System.out.println("   Creating Carrot:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				boolean leaves = readBool("     Have it gor leaves?(1-yes;0-no): ");
				System.out.println("-------------------------------------------------------");
				Carrot newCarrot = new Carrot(name, producer, quantity,currency_period, leaves);
				System.out.println("New Carrot was created: "+ newCarrot.getName() + ", " + newCarrot.getProducer() + " : " +newCarrot.howMuchLeft()+" "+ newCarrot.hasLeaves() +" [" + (newCarrot.isExpired()?"INVALID":"valid") + "]"+" ..." );
				return newCarrot;
			}
			else if(foodType.equals("cheese")){
				System.out.println("   Creating Cheese:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				String type = readString("     Provide type: ");
				System.out.println("-------------------------------------------------------");
				Cheese newCheese = new Cheese(name, producer, quantity,currency_period, type);
				System.out.println("New Cheese was created: "+ newCheese.getName() + ", " + newCheese.getProducer() + " : " +newCheese.howMuchLeft()+" "+ newCheese.getType() +" [" + (newCheese.isExpired()?"INVALID":"valid") + "]"+" ..." );
				return newCheese;
			}
			else if(foodType.equals("milk")){
				System.out.println("   Creating Milk:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				boolean uht = readBool("     Is it UHT milk?(1-yes;0-no): ");
				System.out.println("-------------------------------------------------------");
				Milk newMilk = new Milk(name, producer, quantity,currency_period, uht);
				System.out.println("New Cheese was created: "+ newMilk.getName() + ", " + newMilk.getProducer() + " : " +newMilk.howMuchLeft()+" "+ newMilk.isUHT() +" [" + (newMilk.isExpired()?"INVALID":"valid") + "]"+" ..." );
				return newMilk;
			}
			else if(foodType.equals("tomatoes")){
				System.out.println("   Creating Tomatoes:");
				String name = readString("     Provide name: ");
				String producer = readString("     Provide producer: ");
				int quantity = readNumber("     Provide quantity: ");
				int currency_period = readNumber("     Provide days to expire: ");
				int softness = readNumber("     Provide softness: ");
				System.out.println("-------------------------------------------------------");
				Tomatoes newTomatoes = new Tomatoes(name, producer, quantity,currency_period, softness);
				System.out.println("New Tomatoes were created: "+ newTomatoes.getName() + ", " + newTomatoes.getProducer() + " : " +newTomatoes.howMuchLeft()+" "+ newTomatoes.getSoftness() +" ..." );
				return newTomatoes;
			}
			else {
				System.err.println("WARNING: Unknown food type!");
			}
			return null;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}



	private void actionDeleteItem() {
		try {

			String name = readString("Please provide name of the item you want to remove: ");
			if (name == null) {
				System.err.println("WARNING: Empty answer - exiting action!");
				return;
			}

			IFood item = _fridge.get(name);
			if (item != null) {
				item = null;
				System.out.println("The item was removed from the fridge.");
			} else {
				System.err.println("WARNING: Item not found - exiting action!");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}


	private String readString(String prompt)throws Exception {
		String userResponse = "";

		// display the prompt
		System.out.print(prompt);

		// wait for user input and read the string provided by the user
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			userResponse = input.readLine();
		} catch (Exception e) {
			throw new Exception("Wrong user input! " + e.getMessage());
		}
		return userResponse;
	}

	/**
	 * Displays a prompt and waits for user input
	 * 
	 * @param prompt	Prompt text that should be displayed beforhand
	 * @return			an integer number given by the user or -1 if something was wrong
	 */
	private int readNumber(String prompt) throws Exception {
		int userResponse = -1;

		// display the prompt
		System.out.print(prompt);

		// wait for user input and read the number provided by the user
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			userResponse = Integer.parseInt( input.readLine() );
		} catch (Exception e) {
			throw new Exception("Wrong user input! " + e.getMessage());
		}
		return userResponse;
	}

	private boolean readBool(String prompt) throws Exception {
		int userResponse = -1;

		// display the prompt
		System.out.print(prompt);

		// wait for user input and read the number provided by the user
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			userResponse = Integer.parseInt( input.readLine() );
		} catch (Exception e) {
			throw new Exception("Wrong user input! " + e.getMessage());
		}
		if (userResponse==1){
			return true;
		}
		else{
			return false;
		}
	}


}
