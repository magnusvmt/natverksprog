import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeService1 {

	public TimeService1() {

	}

	public static Locale getLanguage(String text) {
		if (text.equals("english")) {
			return Locale.ENGLISH;
		} else if (text.equals("german")) {
			return Locale.GERMAN;
		} else if (text.equals("french")) {
			return Locale.FRENCH;
		} else {
			return Locale.getDefault();
		}
	}

	public static void main(String[] args){
		DateFormat df = null;
		switch (args[0]) {
		case "date":
			df = DateFormat.getDateInstance(DateFormat.FULL,
					getLanguage(args[1]));
			break;
		case "time":
			df = DateFormat.getTimeInstance(DateFormat.FULL,
					getLanguage(args[1]));
			break;
		default:
			System.out.println("invalid input");
		}
		System.out.println(df.format(new Date()));
	}

}
