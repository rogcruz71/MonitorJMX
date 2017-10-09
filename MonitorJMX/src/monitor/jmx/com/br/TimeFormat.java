package monitor.jmx.com.br;

/**
 * @author Rogerio Cruz
 *
 */
public class TimeFormat {

	public String millisToTime(long milliSeconds) {
		
		//convert milliseconds to human time

		String upTime;
		
		long timeMillis = milliSeconds;
		long time = timeMillis / 1000;  
		String seconds = Integer.toString((int)(time % 60));  
		String minutes = Integer.toString((int)((time % 3600) / 60));  
		String hours = Integer.toString((int)(time / 3600));  
		for (int j = 0; j < 2; j++) {  
			if (seconds.length() < 2) {  
				seconds = "0" + seconds;  
			}  
			if (minutes.length() < 2) {  
				minutes = "0" + minutes;  
			}  
			if (hours.length() < 2) {  
				hours = "0" + hours;  
			}  
		}  		
		
		return upTime = hours + ":" + minutes + ":" + seconds;
	}
}
