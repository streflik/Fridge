package pl.kti.pl.fridge.basic;


import pl.kti.pk.fridge.*;


public class BasicRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		Fridge myFridge = new Fridge();
		IFood cola = myFridge.get("Garden carrots");
		
		IFood mirinda = myFridge.get("Mirinda");
		System.out.println(cola.getName() + ", " + cola.getProducer() + " : " + cola.howMuchLeft() + " " + cola.getUnits());
		cola.consume(1);
		
		while(mirinda.howMuchLeft()>0){
			System.out.println(mirinda.getName() + ", " + mirinda.getProducer() + " : " + mirinda.howMuchLeft() + " " + mirinda.getUnits());
			mirinda.consume(1);
		}
		cola.consume(-3);//dodanie produktu
		if (cola.howMuchLeft()>0) {
			myFridge.put(cola);
			
		}
		else
		{
			myFridge.put(cola);
			myFridge.put(cola);
			//myFridge.put(new Carrot("Garden carrots", "GreenCity Garden", 1, false));
		}
				
		System.out.println(cola.getName() + ", " + cola.getProducer() + " : " + cola.howMuchLeft() + " " + cola.getUnits());
	}


	
}
