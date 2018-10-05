import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Battery implements java.rmi.Remote {

	public final static int PULSE_TIME = 10; 		// ms
	
	
	private final int BATTERY_SIMULATOR_TIME = 20; 	//ms
	private final double CAPACITY = 400; 			// Kwh
	// this variable represents how many Kwh left in the battery
	private double energyLeft;						// Kwh
	
	public Battery() {
		this.energyLeft = 400;
		this.calculateEnergyLevel();
		this.calculateDistanceLeft();
	}
	
	/**
	 * later add code to check if we're in a safe level
	 */
	private double calculateEnergyLevel() {
		return Math.round((this.energyLeft / this.CAPACITY) * 100.00);
	}
	
	/**
	 * 
	 */
	private double calculateDistanceLeft() {
		return (this.energyLeft * 1.5);
	}
	
	public double getEstimatedDistanceLeft() {
		return this.calculateDistanceLeft();
	}
	
	public double getEnergyLeft() {
		return this.energyLeft;
	}

	public double getEnergyLevel() {
		return this.calculateEnergyLevel();
	}
	
	public double getDistanceLefet() {
		return this.calculateDistanceLeft();
	}
	
	private class BatterySimulator extends Thread {
		
		@Override
		public void run() {
			Random rand = new Random();
			int randValue;
			while (energyLeft > 0) {
				try {
					TimeUnit.MILLISECONDS.sleep(BATTERY_SIMULATOR_TIME);
					randValue = rand.nextInt(6);
					energyLeft -= randValue;
					energyLeft = (energyLeft < 0) ? 0 : energyLeft;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void startSimulation() {
		BatterySimulator sim = new BatterySimulator();
		sim.start();
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "Energy level: " + this.getEnergyLevel() + "%\n";
		result += "Distance left: " + getDistanceLefet();
		return result;
	}
	
	public static void main(String[] args) {
		
		Battery battery = new Battery();
		battery.startSimulation();
		boolean going = true;
		double energyLeft;
		while (going) {
			try {
				TimeUnit.MILLISECONDS.sleep(2);
				energyLeft =  battery.getEnergyLevel();
				going = (energyLeft > 0) ? going : false;
				System.out.println(battery);
				System.out.println("-----------------------------------------------\n\n");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Battery needs to be charged");
	}
	
}
