import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class BatteryChecker extends UnicastRemoteObject implements BatteryCheckerInterface {

	private static final long serialVersionUID = 1L;
	private final int WAITING_THRESHOLD = 4;

	private int pulseTime;
	private volatile boolean batteryWorking;
	private DateTimeFormatter dtf;
	private SimoulatorInterface sim;
//	private PulseChecker pChecker;

	public BatteryChecker() throws RemoteException {
		this.batteryWorking = true;
		this.dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.sim = null;
	}

	public synchronized void notifySim() throws RemoteException {
		if (sim == null) {
			LocalDateTime now = LocalDateTime.now();
			System.err.println(dtf.format(now) + ": Simulator not binded, exiting");
			System.exit(1);
		}
		this.sim.switchBattery();
	}

//	public void start() {
//		LocalDateTime now;
//		while (true) {
//			try {
//				TimeUnit.MILLISECONDS.sleep(pulseTime);
//				if (!batteryWorking) {
//					now = LocalDateTime.now();
//					System.err.println(dtf.format(now) + ": " + batName + " is dead!");
//					System.err.println(dtf.format(now) + ": Notifying the Simulator to change batteries");
//					notifySim();
//					break;
//				}
//				batteryWorking = false;
//				now = LocalDateTime.now();
//				System.out.println(dtf.format(now) + ": " + batName + " is alive!");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
	private class PulseChecker extends Thread {

//		private String name;
//		private boolean working;
//		
//		public PulseChecker(String name) {
//			this.name = name;
//		}
//		
//		public void reportAlive() {
//			this.working = true;
//		}
//		
		@Override
		public synchronized void run() {
			LocalDateTime now;
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(pulseTime);
					if (!batteryWorking) {
						now = LocalDateTime.now();
//						System.err.println(dtf.format(now) + ": " + this.name + " is dead!");
						System.err.println(dtf.format(now) + ": Battery is dead!");
						System.err.println(dtf.format(now) + ": Notifying the Simulator to change batteries");
						notifySim();
						break;
					}
					batteryWorking = false;
					now = LocalDateTime.now();
//					System.out.println(dtf.format(now) + ": " + this.name + " is alive!");
					System.out.println(dtf.format(now) + ": Battery is alive!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public synchronized void bindSim(SimoulatorInterface sim) {
		this.sim = sim;
	}

	@Override
	public synchronized void startPulse() throws RemoteException {
		PulseChecker pChecker = new PulseChecker();
		pChecker.start();
//		this.start();
	}

	@Override
	public synchronized void reportAlive() throws RemoteException {
		this.batteryWorking = true;
//		this.pChecker.reportAlive();
	}

	@Override
	public synchronized void getTimeInterval(int time) throws RemoteException {
		this.pulseTime = time + WAITING_THRESHOLD;
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		BatteryChecker bChecker = new BatteryChecker();
		Naming.rebind("BatteryChecker", bChecker);
		System.out.println("BatteryChecker ready");
	}
}
