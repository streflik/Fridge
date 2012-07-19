package pl.kti.pl.fridge.data;
import java.io.BufferedWriter;
import java.io.IOException;

import pl.kti.pk.fridge.AFood;


public class Milk extends AFood{

	protected boolean _uht;  
	protected String _units="ml";

	public Milk(String _name, String _producer, int _hasMuchLeft,int _currencyPeriod,
			boolean _uht) throws Exception {
		super(_name, _producer, _hasMuchLeft,_currencyPeriod);
		this._uht = _uht;
	}

	public boolean isUHT(){
		return _uht;

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
		return (_uht? "UHT" : "not UHT");
		
	}

	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("MILK\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_uht + "\n");
		
	}


}
