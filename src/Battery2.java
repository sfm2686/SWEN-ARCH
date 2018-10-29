import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Battery2 extends Battery {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Battery2(BatteryCheckerInterface batteryChecker) throws RemoteException {
		super(batteryChecker, 0, 100000, 0, 10);
	}
	
//	@Override
//	public void sync(double energyLeft) throws RemoteException {
//		this.energyLeft = energyLeft;
//		LocalDateTime now = LocalDateTime.now();
//		System.err.println(dtf.format(now) + ": Synched with battery1");
//	}
	
	public void causeException() {
		int randValue = this.rand.nextInt(this.maxRangeEx - this.minRangeEx) + this.minRangeEx;
		if (randValue == 0) { // random number to throw an exception
			@SuppressWarnings("unused")
			int bad = 20 / 0;  // INSERT CHANCE
		}
	}
	
	public void deductEnergy() {
		double randValue = (this.maxRangeRand - this.minRangeRand) * this.rand.nextDouble() + this.minRangeRand;
		this.energyLeft -= randValue;
		this.energyLeft = (this.energyLeft < 0) ? 0 : this.energyLeft;
		
		/* uncomment this if we decide to sync both directions */
//		if (this.batteryToSync != null) {
//			this.batteryToSync.sync(this.energyLeft);
//		}

		this.working = randValue >= 0 && randValue < 0.01 ? false : true; // INSERT CHANCE chance of random failure
	}

	// needs checker to be online
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
		Battery battery = new Battery2(bChecker);
		Naming.rebind("Battery2", battery);
	}
}