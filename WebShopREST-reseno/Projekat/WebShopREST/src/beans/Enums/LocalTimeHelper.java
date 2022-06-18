package beans.Enums;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeHelper {
	
public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	
	public static String timeToString(LocalTime time) {
		return time.format(timeFormat);
	}
	
	public static LocalTime stringToDate(String time) {
		return LocalTime.parse(time, timeFormat);
	}

}
