import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class FaultDetector {

	private DateTimeFormatter dtf;

	public FaultDetector() {
		this.dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	}

	public void normalOperation() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(this.dtf.format(now) + ": Battery is alive!");
	}

	public void reportFault() {
		LocalDateTime now = LocalDateTime.now();
		System.err.println(this.dtf.format(now) + ": Battery is dead!");
	}
}
