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
	protected Random rand;
	protected Battery batteryToSync;
	
	public Battery(BatteryCheckerInterface checker) {
		this.energyLeft = DEFAULT_STARTING_ENERGY;
		this.working = true;
		this.batteryChecker = checker;
		this.rand = new Random();
	}
	
	public void setBatteryToSync(Battery battery) {
		this.batteryToSync = battery;
	}
	
	public void sync(double energyLeft) {
		this.energyLeft = energyLeft;
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
	
	public void startHeartBeat() throws RemoteException {
		// initialize and start pulse sender
		BatteryPulse bPulse = new BatteryPulse();
		bPulse.start();
		this.batteryChecker.getTimeInterval(PULSE_TIME);
		// call batteryChecker object to start taking the pulse
		this.batteryChecker.startPulse();
	}
	
	public boolean isWorking() {
		return this.working;
	}
	
	protected double calculateEnergyLevel() {
		// notify battery2
		double energyLevel = (this.energyLeft / this.CAPACITY) * 100;
		return energyLevel;
	}

	protected double calculateDistanceLeft() {
		// notify battery2
		return Math.round((this.energyLeft * 1.5) * 100.0) / 100.0;
	}
	
	@Override
	public String toString() {
		String result = "";
		// formatting 2 decimal places percent for energyLeft
		result += this.getClass() + "\n";
		result += String.format("%s%.2f%s", "Energy level: ", this.calculateEnergyLevel(), "%\n");
		result += "Distance left: " + this.calculateDistanceLeft() + "km\n";
		result += "------------------------------------------------------\n\n";
		return result;
	}
	
	public abstract void causeException();
	public abstract void deductEnergy();
}
