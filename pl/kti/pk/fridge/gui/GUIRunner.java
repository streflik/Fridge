package pl.kti.pk.fridge.gui;
import pl.kti.pk.fridge.*;
import pl.kti.pl.fridge.data.*;
import pl.kti.pk.fridge.tui.*;

public class GUIRunner{


	public static void main(String[] args) throws Exception {
		
		Fridge myFridge = new Fridge();
		myFridge.put(new Carrot("Garden carrots", "GreenCity Garden",3, 7, false));
		FridgeFrame frame = new FridgeFrame(myFridge);
		frame.setVisible(true);
		frame.actionLoadProducts();
		Clock clock = new Clock(frame);
		clock.start();
		
	}
}
