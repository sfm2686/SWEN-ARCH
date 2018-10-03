import java.util.Random;

public class battery {

	public final static int PULSE_TIME = 10; 		// ms
	private final int BATTERY_SIMULATOR_TIME = 20; 	//ms
	private final double CAPACITY = 400; 			// Kwh
	// this variable represents how many Kwh left in the battery
	private double energyLeft;						// Kwh
	
	public battery() {
		this.energyLeft = 400;
		this.calculateEnergyLevel();
		this.calculateDistanceLeft();
	}
	
	/**
	 * later add code to check if we're in a safe level
	 */
	private double calculateEnergyLevel() {
		return Math.round((this.energyLeft / this.CAPACITY) * 100.00) / 100.0;
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
	
	private class BatterySimulator extends Thread {
		
		@Override
		public void run() {
			Random rand = new Random();
			int randValue;
			while (true) {
				try {
					sleep(BATTERY_SIMULATOR_TIME);
					randValue = rand.nextInt(40) + 2;
					energyLeft -= randValue;
					calculateEnergyLevel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
