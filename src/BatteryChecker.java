import java.util.concurrent.TimeUnit;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class BatteryChecker extends UnicastRemoteObject implements BattreyCheckerIntereface {

	private int pulseTime;
	private FaultDetector faultD;
	
	public BatteryChecker() throws RemoteException {
//		this.pulseTime = Battery.PULSE_TIME;
		this.faultD = new FaultDetector();
	}
	
	public void checkAlive() {
		boolean alive = true;
		while (alive) {
			// logic to get heartbeat from battery, set alive to false if it was not received
			;
		}
		this.faultD.reportFault();
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("BattereyCheckere ran");
	}

	@Override
	public int getTime(int time) throws RemoteException {
		System.out.println("geteTime in BatteryChecker was calllllllled");
		System.out.println("called with time: " + time);
		return time;
	}
}
