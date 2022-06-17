package beans.Enums;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeHelper {

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm:ss");
	
	public static String dateToString(LocalDateTime date) {
		return date.format(dateFormat);
	}
	
	public static LocalDateTime stringToDate(String date) {
		return LocalDateTime.parse(date, dateFormat);
	}
}
