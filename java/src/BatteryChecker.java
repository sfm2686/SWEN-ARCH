import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BatteryChecker extends UnicastRemoteObject implements BatteryCheckerInterface {

	private static final long serialVersionUID = 1L;
	private final int WAITING_THRESHOLD = 4;

	private int pulseTime;
	private boolean batteryWorking;
	private FaultDetector faultD;

	public BatteryChecker() throws RemoteException {
		this.batteryWorking = true;
		this.faultD = new FaultDetector();
	}

	private class PulseChecker extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(pulseTime);
					if (!batteryWorking) {
						faultD.reportFault();
						break;
					}
					batteryWorking = false;
					faultD.normalOperation();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void startPulse() throws RemoteException {
		PulseChecker pChecker = new PulseChecker();
		pChecker.start();
	}

	@Override
	public void reportAlive() throws RemoteException {
		this.batteryWorking = true;
	}

	@Override
	public void getTimeInterval(int time) throws RemoteException {
		this.pulseTime = time + WAITING_THRESHOLD;
	}

	public static void main(String[] args) {
		try {
			BatteryChecker bChecker = new BatteryChecker();
			Naming.rebind("BatteryChecker", bChecker);
			System.out.println("BatteryChecker ready");
		} catch(MalformedURLException ae) {
			ae.printStackTrace();
		} catch(RemoteException re) {
			re.printStackTrace();
		}
	}
}
