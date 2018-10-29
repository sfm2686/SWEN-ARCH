import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Battery1 extends Battery {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BatteryInterface batteryToSync;
	
	public Battery1(BatteryCheckerInterface batteryChecker, BatteryInterface batteryToSync) throws RemoteException {
		super(batteryChecker, 0, 1000, 0, 10);
		this.batteryToSync = batteryToSync;
	}
	
	public void causeException() {
		int randValue = this.rand.nextInt(this.maxRangeEx - this.minRangeEx) + this.minRangeEx;
		if (randValue == 500) { // random number to throw an exception
			@SuppressWarnings("unused")
			int bad = 20 / 0;  // 0.1% chance
		}
	}

	public void deductEnergy() {
		double randValue = (this.maxRangeRand - this.minRangeRand) * this.rand.nextDouble() + this.minRangeRand;
		this.energyLeft -= randValue;
		this.energyLeft = (this.energyLeft < 0) ? 0 : this.energyLeft; 
		if (this.batteryToSync != null) {
			try {
				this.batteryToSync.sync(this.energyLeft);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		this.working = randValue >= 0 && randValue < 1 ? false : true; // INSERT CHANCE OF RANDOM FAILURE
	}

	// needs checker, battery2 to be online
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
		BatteryInterface b2 = (BatteryInterface) registry.lookup("Battery2");
		Battery battery = new Battery1(bChecker, b2);
		Naming.rebind("Battery1", battery);
	}
}
