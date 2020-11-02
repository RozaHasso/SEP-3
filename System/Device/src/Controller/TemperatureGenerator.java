package Controller;

import Model.Temperature;

import java.time.Instant;
import java.util.Observable;
import java.util.Random;

/**
* A class for generating temperature measurements measurements
* 
* @author Florin Ciornei
* 
*/
public class TemperatureGenerator extends Observable {

	public void start() {
		Runnable run = new Runnable() {
			public void run() {
				Random rnd = new Random();
				while (true) {
					try {
						Temperature measurement=new Temperature(Controller.patientId, Instant.now().toString(),  35+2*rnd.nextDouble());
						setChanged();
						notifyObservers(measurement);
						Thread.sleep(1000 + rnd.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(run).start();
	}


}
