package Controller;

import Model.BloodPressure;
import java.time.Instant;
import java.util.Observable;
import java.util.Random;


/**
* A class for generating blood pressure measurements
* 
* @author Florin Ciornei
* 
*/

public class BloodPresureGenerator extends Observable {

	public void start() {
		Runnable run = new Runnable() {
			public void run() {
				Random rnd = new Random();
				while (true) {
					try {
						BloodPressure measurement = new BloodPressure(Controller.patientId, Instant.now().toString(), 100+rnd.nextInt(40), 70+rnd.nextInt(30));
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
