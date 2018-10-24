import java.rmi.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Battery implements Remote {

	// consts
	protected final int PULSE_TIME = 200;
	protected final int BATTERY_SIMULATOR_TIME = 300;
	protected final double CAPACITY = 400;
	protected final double DEFAULT_STARTING_ENERGY = 400;
	
	
	protected double energyLeft; // how much kwh left
	protected boolean working;
	protected BatteryCheckerInterface batteryChecker; // remote batteryChecker object
	
	
	public Battery(BatteryCheckerInterface checker) throws RemoteException {
		this.energyLeft = DEFAULT_STARTING_ENERGY;
		this.working = true;
		this.batteryChecker = checker;
		this.batteryChecker.getTimeInterval(PULSE_TIME);
		// initialize and start battery simulator and pulse sender
		BatteryPulse bPulse = new BatteryPulse();
		bPulse.start();
		// call batteryChecker object to start taking the pulse
		this.batteryChecker.startPulse();
		// this.pulse();
	}
	
	// send pulse to remote object
	protected class BatteryPulse extends Thread {

		@Override
		public synchronized void run() {
			while (working) {
				try {
					TimeUnit.MILLISECONDS.sleep(PULSE_TIME);
					batteryChecker.reportAlive();
					causeException();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public abstract void causeException();
	public abstract void deductEnergy();
	
	protected double calculateEnergyLevel() {
		// notify battery2
		double energyLevel = (this.energyLeft / this.CAPACITY) * 100;
		return energyLevel;
	}

	protected double calculateDistanceLeft() {
		// notify battery2
		return Math.round((this.energyLeft * 1.5) * 100.0) / 100.0;
	}
}
