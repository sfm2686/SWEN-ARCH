import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

public class Simoulator extends UnicastRemoteObject implements SimoulatorInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int BATTERY_SIMULATOR_TIME = 1500;
	
	private BatteryInterface currentBatt, redundantBatt;
	
	public Simoulator(BatteryInterface b1, BatteryInterface b2, BatteryCheckerInterface checker) throws RemoteException {
		this.currentBatt = b1;
		this.redundantBatt = b2;
		checker.bindSim(this);
		this.simulate();
	}
	
	@Override
	public synchronized void switchBattery() throws RemoteException {
		this.currentBatt = this.redundantBatt;
		System.out.println("\n\n------------------------------------");
		System.out.println("Switched from Battery1 to Battery2");
		System.out.println("------------------------------------");
	}
	
	public void simulate() throws RemoteException {
		while (this.currentBatt.getEnergyLeft() > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(BATTERY_SIMULATOR_TIME);
				this.currentBatt.deductEnergy();
				System.out.println("\n\n------------------------------------");
				System.out.println("Using: " + currentBatt.getName());
				System.out.println(currentBatt.getEnergyLeft() + " energy left");
				System.out.println("------------------------------------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (!this.currentBatt.isWorking()) {
			System.out.println("Battery Died");
		} else {
			System.out.println("Battery needs to be recharged");
		}
	}
	
	// needs batteries and checker to be online
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		BatteryInterface b1 = (BatteryInterface) registry.lookup("Battery1");
		BatteryInterface b2 = (BatteryInterface) registry.lookup("Battery2");
		BatteryCheckerInterface checker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
		Simoulator sim = new Simoulator(b1, b2, checker);
		Naming.rebind("Simulator", sim);
	}
}
