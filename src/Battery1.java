import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Battery1 extends Battery implements java.rmi.Remote {


	public Battery1(BatteryCheckerInterface batteryChecker) throws RemoteException {
		super(batteryChecker);
	}

//	private class BatterySimulator extends Thread {
//
//		@Override
//		public synchronized void run() {
//			Random rand = new Random();
//			double randValue;
//			double minRange = 0;
//			double maxRange = 10;
//			while (energyLeft > 0 && working) {
//				try {
//					TimeUnit.MILLISECONDS.sleep(BATTERY_SIMULATOR_TIME);
//					// Random value between min and max range vars defined above
//					randValue = minRange + (maxRange - minRange) * rand.nextDouble(); // not needed
//
//					// randomly kill battery
//					working = randValue >= 0 && randValue < 0.2 ? false : true; // 2% chance NOT NEEDED
//
//					energyLeft -= randValue; // call deductEnergy instead of this line
//					// Make sure energyLeft does not go under 0
//					energyLeft = (energyLeft < 0) ? 0 : energyLeft;
//					// Update battery state and print battery
//					// calculateEnergyLevel();
//					// calculateDistanceLeft();
//					System.out.println(Battery1.this);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!working)  {
//				System.out.println("Battery died!"); // random failure
//			} else {
//				System.out.println("Battery needs to be recharged"); // happy path
//			}
//			Thread.currentThread().interrupt();
//		}
//	}
	
	public void causeException() {
		Random rand =  new Random();
		int randValue = rand.nextInt(1000 - 0) + 0;
		if (randValue == 500) { // random number to throw an exception
			int bad = 20 / 0;  // 0.1% chance
		}
	}
	
	public void deductEnergy() {
		// TODO Auto-generated method stub
		System.out.println("deduct energy");
	}

	@Override
	public String toString() {
		String result = "";
		// formatting 2 decimal places percent for energyLeft
		result = String.format("%s%.2f%s", "Energy level: ", this.calculateEnergyLevel(), "%\n");
		result += "Distance left: " + this.calculateDistanceLeft() + "km\n";
		result += "------------------------------------------------------\n\n";
		return result;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
		Battery1 battery = new Battery1(bChecker);
	}
}
