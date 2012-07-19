package pl.kti.pk.fridge;

import java.io.*;
import pl.kti.pk.fridge.tui.*;

public abstract class AFood implements IFood {

	protected String _name;
	protected String _producer;
	protected int _quantity;
	protected int _currencyTime;
	protected int _creationTime;


	public AFood(String name, String producer, int quantity, int currencyTime) throws Exception {
		if (name.equals("")){
			throw new Exception("Cannot create item with empty name!");
		}
		this._name = name;
		this._producer = producer;
		this._quantity = quantity;
		this._currencyTime = currencyTime;
		this._creationTime = Clock.getClock().getTime();
	}


	public abstract void consume(int quantity) throws Exception ;
	public abstract String getUnits();

	public abstract String getAdditionalInfo(); 


	public String getName() {
		// TODO Auto-generated method stub
		return _name;
	}


	public String getProducer() {
		// TODO Auto-generated method stub
		return _producer;
	}


	public int howMuchLeft() {
		// TODO Auto-generated method stub
		return _quantity;
	}

	public void serializeStandard(BufferedWriter bwriter) throws IOException{
		bwriter.write(_name + "\n");
		bwriter.write(_producer + "\n");
		bwriter.write(_quantity + "\n");
		bwriter.write(_currencyTime + "\n");
	}


	public boolean isExpired() {	
		boolean _useful = false;
		int _actualTime=Clock.getClock().getTime()+_creationTime;

		if (_actualTime>_currencyTime) {
			_useful= true;
		}
		return _useful;
	} 

}

