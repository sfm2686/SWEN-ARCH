import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Battery implements java.rmi.Remote {

	private final int PULSE_TIME = 200;
	private final int BATTERY_SIMULATOR_TIME = 300;
	private final double CAPACITY = 400;
	private final double DEFAULT_STARTING_ENERGY = 400;

	private double energyLeft; // how much kwh left
	private boolean working;
	private BatteryCheckerInterface batteryChecker; // remote batteryChecker object

	public Battery(BatteryCheckerInterface batteryChecker) throws RemoteException {
		this.energyLeft = DEFAULT_STARTING_ENERGY;
		this.working = true;
		this.batteryChecker = batteryChecker;
		this.batteryChecker.getTimeInterval(PULSE_TIME);
		// initialize and start battery simulator and pulse sender
		BatterySimulator bSim = new BatterySimulator();
		BatteryPulse bPulse = new BatteryPulse();
		bPulse.start();
		bSim.start();
		// call batteryChecker object to start taking the pulse
		this.batteryChecker.startPulse();
		// this.pulse();
	}

	private double calculateEnergyLevel() {
		double energyLevel = (this.energyLeft / this.CAPACITY) * 100;
		return energyLevel;
	}

	private double calculateDistanceLeft() {
		return Math.round((this.energyLeft * 1.5) * 100.0) / 100.0;
	}

	private class BatterySimulator extends Thread {

		@Override
		public synchronized void run() {
			Random rand = new Random();
			double randValue;
			double minRange = 0;
			double maxRange = 10;
			while (energyLeft > 0 && working) {
				try {
					TimeUnit.MILLISECONDS.sleep(BATTERY_SIMULATOR_TIME);
					// Random value between min and max range vars defined above
					randValue = minRange + (maxRange - minRange) * rand.nextDouble();

					// randomly kill battery
					working = randValue >= 0 && randValue < 0.2 ? false : true; // 2% chance

					energyLeft -= randValue;
					// Make sure energyLeft does not go under 0
					energyLeft = (energyLeft < 0) ? 0 : energyLeft;
					// Update battery state and print battery
					calculateEnergyLevel();
					calculateDistanceLeft();
					System.out.println(Battery.this);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!working)  {
				System.out.println("Battery died!"); // random failure
			} else {
				System.out.println("Battery needs to be recharged"); // happy path
			}
			Thread.currentThread().interrupt();
		}
	}

	// use for debugging battery sim
	public void startSimulation() {
		BatterySimulator sim = new BatterySimulator();
		sim.start();
	}

	public void causeException() {
		Random rand =  new Random();
		int randValue = rand.nextInt(1000 - 0) + 0;
		if (randValue == 500) { // random number to throw an exception
			int bad = 20 / 0;  // 0.1% chance
		}
	}

	// send pulse to remote object
	private class BatteryPulse extends Thread {

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

	@Override
	public String toString() {
		String result = "";
		// formatting 2 decimal places percent for energyLeft
		result = String.format("%s%.2f%s", "Energy level: ", this.calculateEnergyLevel(), "%\n");
		result += "Distance left: " + calculateDistanceLeft() + "km\n";
		result += "------------------------------------------------------\n\n";
		return result;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
		Battery battery = new Battery(bChecker);
	}
}
