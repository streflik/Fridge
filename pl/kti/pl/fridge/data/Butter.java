package pl.kti.pl.fridge.data;
import pl.kti.pk.fridge.AFood;
import java.io.*;

public class Butter extends AFood {

	protected int _vegetableFats;
	protected int _animalFats;
	protected String _units="grams";

	public Butter	(String _name, String _producer, int _hasMuchLeft,int _currencyPeriod,
			int fats, int fats2) throws Exception {
		super(_name,_producer,_hasMuchLeft,_currencyPeriod);
		_vegetableFats = fats;
		_animalFats = fats2;
	}

	@Override
	public String getUnits() {
		// TODO Auto-generated method stub
		return _units;
	}


	public int getVegetableFats(){
		return _vegetableFats;
	} 

	public int getAnimalFats(){
		return _animalFats;
	}

	@Override
	public void consume(int quantity) throws Exception {
		if (_quantity<quantity){
			throw new Exception("Cannot consume more than available ["+_quantity+"<"+quantity+"]");
		}
		_quantity-=quantity;

	}


	@Override
	public String getAdditionalInfo() {
		// TODO Auto-generated method stub
		return ("Animal fats: " + _animalFats + "<br/> Vegetable fats: "+ _vegetableFats + " <br/>");
	}

	public void serialize(BufferedWriter bwriter) throws IOException {
		// type
		bwriter.write("BUTTER\n");
		// standard attributes
		serializeStandard(bwriter);
		// specific attributes
		bwriter.write(_animalFats + "\n");
		bwriter.write(_vegetableFats + "\n");
	}

}
