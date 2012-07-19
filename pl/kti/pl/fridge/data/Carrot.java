package pl.kti.pl.fridge.data;
import java.io.BufferedWriter;
import java.io.IOException;

import pl.kti.pk.fridge.AFood;


public class Carrot extends AFood{

	protected  boolean _leaves;
	protected String _units="carrots";


	public Carrot(String _name, String _producer, int _hasMuchLeft,int _currencyPeriod,
			boolean _leaves) throws Exception {
		super( _name, _producer, _hasMuchLeft,_currencyPeriod);
		this._leaves = _leaves;
	}



	public boolean hasLeaves(){
		return _leaves;
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
		return (_leaves ? "with leaves" : "Without leaves");
	}

	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("CARROT\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_leaves + "\n");

	}


}
