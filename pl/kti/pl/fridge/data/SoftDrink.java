package pl.kti.pl.fridge.data;
import java.io.BufferedWriter;
import java.io.IOException;

import pl.kti.pk.fridge.AFood;


public class SoftDrink extends AFood{

	protected String _flavour;
	protected String _units="ml";

	public SoftDrink(String _name, String _producer,int _currencyPeriod,
			int _hasMuchLeft, String _flavour) throws Exception {
		super(_name, _producer, _hasMuchLeft,_currencyPeriod);
		this._flavour = _flavour;
	}



	@Override
	public void consume(int quantity) throws Exception {
		if (_quantity<quantity){
			throw new Exception("Cannot consume more than available ["+_quantity+"<"+quantity+"]");
		}
		_quantity-=quantity;
		
	}
	
	
	public String getFlavour(){

		return _flavour;
	}

	public String getUnits() {
		// TODO Auto-generated method stub
		return _units;
	}

	@Override
	public String getAdditionalInfo() {
		// TODO Auto-generated method stub
		return "Flavour: " + _flavour;
	}

	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("SOFTDRINK\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_flavour + "\n");
		
	}

}
