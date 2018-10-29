import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Battery extends UnicastRemoteObject implements BatteryInterface {

	// consts
	protected final int PULSE_TIME = 1000;
	protected final double CAPACITY = 400;
	protected final double DEFAULT_STARTING_ENERGY = 400;
	
	protected double energyLeft; // how much kwh left
	protected boolean working;
	protected BatteryCheckerInterface batteryChecker; // remote batteryChecker object
	protected Random rand;
//	protected Battery batteryToSync;
	protected int minRangeEx, maxRangeEx;
	protected double minRangeRand, maxRangeRand;
	
	public Battery(BatteryCheckerInterface checker, int minRangeEx, int maxRangeEx, double minRangeRand, double maxRangeRand) throws RemoteException {
		this.energyLeft = DEFAULT_STARTING_ENERGY;
		this.working = true;
		this.batteryChecker = checker;
		this.rand = new Random();
//		this.batteryToSync = null;
		this.minRangeEx = minRangeEx;
		this.maxRangeEx = maxRangeEx;
		this.minRangeRand = minRangeRand;
		this.maxRangeRand = maxRangeRand;
		this.startHeartBeat();
		System.out.println(Battery.this.getClass().getSimpleName() + ": is working and started sending out heartbeat");
	}
	
//	public void setBatteryToSync(Battery battery) {
//		this.batteryToSync = battery;
//	}
	
//	public void sync(double energyLeft) {
//		this.energyLeft = energyLeft;
//	}
	
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
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public double getEnergyLeft() {
		double roundOff = Math.round(this.energyLeft * 100.0) / 100.0;
		return roundOff;
	}
	
	public void sync(double energyLeft) throws RemoteException {
		this.energyLeft = energyLeft;
	}
	
	public void startHeartBeat() throws RemoteException {
		this.batteryChecker.getTimeInterval(PULSE_TIME);
		// initialize and start pulse sender
		BatteryPulse bPulse = new BatteryPulse();
		bPulse.start();
		// call batteryChecker object to start taking the pulse
		this.batteryChecker.startPulse();
	}
	
	public boolean isWorking() {
		return this.working;
	}
	
	protected double calculateEnergyLevel() {
		double energyLevel = (this.energyLeft / this.CAPACITY) * 100;
		return energyLevel;
	}

	protected double calculateDistanceLeft() {
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
}
