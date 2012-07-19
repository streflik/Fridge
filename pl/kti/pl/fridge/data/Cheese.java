package pl.kti.pl.fridge.data;
import java.io.BufferedWriter;
import java.io.IOException;

import pl.kti.pk.fridge.AFood;


public class Cheese extends AFood{

    protected String _type;
    protected String _units="grams";
    

	public Cheese(String _name, String _producer,int _hasMuchLeft,int _currencyPeriod,
			 String _type) throws Exception {
		super(_name, _producer, _hasMuchLeft,_currencyPeriod);
		this._type = _type;
	}

        
	public String getType(){
		return _type;
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
		return "Type: " + _type;
	}
           
	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("CHEESE\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_type + "\n");
		
	}

}
