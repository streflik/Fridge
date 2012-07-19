package pl.kti.pl.fridge.data;
import java.io.BufferedWriter;
import java.io.IOException;

import pl.kti.pk.fridge.AFood;


public class Tomatoes extends AFood{
   
	protected int _softness;  
	protected String _units="tomatoes";

	public Tomatoes(String _name, String _producer, int _hasMuchLeft,int _currencyPeriod,
			int _softness) throws Exception {
		super(_name, _producer, _hasMuchLeft,_currencyPeriod);
		this._softness = _softness;
	}


	public int getSoftness(){
		return _softness;
	}

	@Override
	public void consume(int quantity) throws Exception {
		if (_quantity<quantity){
			throw new Exception("Cannot consume more than available ["+_quantity+"<"+quantity+"]");
		}
		_quantity-=quantity;
		
	}

	public String getUnits() {
		// TODO Auto-generated method stub
		return _units;
	}

	@Override
	public String getAdditionalInfo() {
		// TODO Auto-generated method stub
		return "Softness: " + _softness;
	}

	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("TOMATOES\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_softness + "\n");
		
	}

}
