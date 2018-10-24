public class Battery2 extends Battery{


	private double minRangeEnergy, maxRangeEnergy;
	private int minRangeException, maxRangeException;
	
	public Battery2(BatteryCheckerInterface batteryChecker) {
		super(batteryChecker);
		this.minRangeEnergy = 0;
		this.maxRangeEnergy = 10;
		
		this.minRangeException = 0;
		this.maxRangeException = 10000;
	}
	
	public void causeException() {
		int randValue = this.rand.nextInt(this.maxRangeException - this.minRangeException) + this.minRangeException;
		if (randValue == 0) { // random number to throw an exception
			int bad = 20 / 0;  // INSERT CHANCE
		}
	}
	
	public void deductEnergy() {
		double randValue = this.minRangeEnergy + (this.maxRangeEnergy - this.minRangeEnergy) * this.rand.nextDouble();
		this.energyLeft -= randValue;
		if (this.batteryToSync != null) {
			this.batteryToSync.sync(this.energyLeft);
		}
		this.working = randValue >= 0 && randValue < 0.1 ? false : true; // INSERT CHANCE chance of random failure
	}
//
//	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
//		Registry registry = LocateRegistry.getRegistry();
//		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
//		Battery1 battery = new Battery1(bChecker);
//	}
}

