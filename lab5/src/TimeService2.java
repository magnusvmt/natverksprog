import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeService2 {

	public TimeService2() {

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

	public static void main(String[] args) throws IOException {
		DateFormat df = null;
		while (true) {
			switch (readString()) {
			case "date":
				df = DateFormat.getDateInstance(DateFormat.FULL,
						getLanguage(readString()));
				break;
			case "time":
				df = DateFormat.getTimeInstance(DateFormat.FULL,
						getLanguage(readString()));
				break;
			default:
				System.out.println("invalid input");
			}
			System.out.println(df.format(new Date()));
		}

	}

	public static String readString() throws IOException {
		StringBuilder sb = new StringBuilder();
		char c;
		while (!Character.isWhitespace(c = (char) System.in.read())) {
			sb.append(c);
		}
		return sb.toString();
	}
}
