package Controller;

import Model.HeartRate;

import java.time.Instant;
import java.util.Observable;
import java.util.Random;

/**
* A class for generating heart rate measurements
* 
* @author Florin Ciornei
* 
*/
public class HeartRateGenerator extends Observable {
	public void start() {
		Runnable run = new Runnable() {
			public void run() {
				Random rnd = new Random();
				while (true) {
					try {
						HeartRate measurement = new HeartRate(Controller.patientId,
								Instant.now().toString(),60 + rnd.nextInt(40));
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
