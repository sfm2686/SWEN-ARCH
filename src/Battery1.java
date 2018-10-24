public class Battery1 extends Battery {


	private double minRangeEnergy, maxRangeEnergy;
	private int minRangeException, maxRangeException;
	
	public Battery1(BatteryCheckerInterface batteryChecker) {
		super(batteryChecker);
		this.minRangeEnergy = 0;
		this.maxRangeEnergy = 10;
		
		this.minRangeException = 0;
		this.maxRangeException = 1000;
	}
	
	public void causeException() {
		int randValue = this.rand.nextInt(this.maxRangeException - this.minRangeException) + this.minRangeException;
		if (randValue == 500) { // random number to throw an exception
			int bad = 20 / 0;  // 0.1% chance
		}
	}
	
	public void deductEnergy() {
		double randValue = this.minRangeEnergy + (this.maxRangeEnergy - this.minRangeEnergy) * rand.nextDouble();
		this.energyLeft -= randValue;
		if (this.batteryToSync != null) {
			this.batteryToSync.sync(this.energyLeft);
		}
		this.working = randValue >= 0 && randValue < 0.2 ? false : true; // 2% chance of random failure
	}
//
//	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
//		Registry registry = LocateRegistry.getRegistry();
//		BatteryCheckerInterface bChecker = (BatteryCheckerInterface) registry.lookup("BatteryChecker");
//		Battery1 battery = new Battery1(bChecker);
//	}
}
