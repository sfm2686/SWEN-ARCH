import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simoulator extends Thread implements SimoulatorInterface  {

	public static void main(String[] args) throws RemoteException {
				BatteryCheckerInterface batteryChecker = null;
				Battery b1 = new Battery1(batteryChecker);
				Battery b2 = new Battery1(batteryChecker);
				run(b1);			
	}
	
				public synchronized static void run(Battery b1) throws RemoteException {
					Random rand = new Random();
					double randValue;
					double minRange = 0;
					double maxRange = 10;
					while (b1.energyLeft > 0 && b1.working) {
						try {
							TimeUnit.MILLISECONDS.sleep(b1.BATTERY_SIMULATOR_TIME);
							randValue = minRange + (maxRange - minRange) * rand.nextDouble(); // not needed
		
							// randomly kill battery
							b1.working = randValue >= 0 && randValue < 0.2 ? false : true; // 2% chance NOT NEEDED
		
							b1.energyLeft -= randValue; // call deductEnergy instead of this line
							// Make sure energyLeft does not go under 0
							b1.energyLeft = (b1.energyLeft < 0) ? 0 : b1.energyLeft;
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!b1.working)  {
						System.out.println("Battery died!"); // random failure
						switchBattery();
					} else {
						System.out.println("Battery needs to be recharged"); // happy path
						switchBattery();
					}
					Thread.currentThread().interrupt();
				}// end run()
			
	
	public static void switchBattery() throws RemoteException {
		System.out.println("Battery has been switched");
		BatteryCheckerInterface batteryChecker = null;
		Battery b2 = new Battery1(batteryChecker);
		run(b2);
	}

}// end class
