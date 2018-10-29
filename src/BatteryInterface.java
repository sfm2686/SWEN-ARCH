import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BatteryInterface extends Remote{

	public void sync(double energyLeft) throws RemoteException;
	public void causeException() throws RemoteException;
	public void deductEnergy() throws RemoteException;
	public boolean isWorking() throws RemoteException;
	public double getEnergyLeft() throws RemoteException;
	public String getName() throws RemoteException;
}
