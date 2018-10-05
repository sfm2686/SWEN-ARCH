import java.rmi.*;

public interface BattreyCheckerIntereface extends Remote {
	
	public int getTime(int time) throws RemoteException;
}
