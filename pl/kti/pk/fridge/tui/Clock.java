package pl.kti.pk.fridge.tui;

import pl.kti.pk.fridge.gui.*;

public class Clock extends Thread{
	private int _time;
	private static Clock _clock;
	public Clock(){
		_time=0;
		_clock=this;
	}
	public Clock(FridgeFrame frame){
		_time=0;
		_clock=this;
	}
	
	public int getTime(){
		return _time;
	}
	public static Clock getClock() {
		if (_clock == null) {
			_clock = new Clock();
			_clock.start();
		}
		return _clock;
	}
	

	public void run() {
		_time = 0;
		System.out.println("CLOCK: Starting the clock thread.");
		try {
			while (true) {
				_time++;
				FridgeFrame.refresh();
				
				sleep(5000);
			}
		} catch (InterruptedException e) {
			System.out.println("CLOCK: thread interrupted.");
		}
		System.out.println("CLOCK: Ending the clock thread.");
	}
	
}
