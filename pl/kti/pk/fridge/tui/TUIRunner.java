package pl.kti.pk.fridge.tui;
import pl.kti.pk.fridge.Fridge;

public class TUIRunner {


	public static void main(String[] args) throws Exception {
		// create fridge
		Clock clock = new Clock();
		clock.start();
		Fridge myFridge = new Fridge();

		// create controller for the fridge
		// notice that we have to pass the fridge object to the controller as its parameter
		FridgeController myFridgeController = new FridgeController(myFridge);

		myFridgeController.run();
		clock.interrupt();
	}

}
