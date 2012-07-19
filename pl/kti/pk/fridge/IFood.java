package pl.kti.pk.fridge;

import java.io.*;
public interface IFood {
	public abstract void consume(int quantity) throws Exception;
	public String getName();
	public String getProducer ();
	public int howMuchLeft ();
	public String getUnits ();
	public String getAdditionalInfo();
	public boolean isExpired();
	public void serialize(BufferedWriter bwriter) throws IOException;
}
