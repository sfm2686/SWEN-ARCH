import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SimoulatorInterface extends Remote {
	
	public void switchBattery() throws RemoteException;
}
