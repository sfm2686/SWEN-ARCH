import java.rmi.*;

public interface BatteryCheckerInterface extends Remote {
	
	public void getTimeInterval(int time) throws RemoteException;
	public void reportAlive() throws RemoteException;
	public void startPulse() throws RemoteException;
}
