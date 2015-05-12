import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeGerman {
	
	public static void main(String[] args){
		 DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.GERMAN);
		 System.out.println(df.format(new Date()));
		 
	}

}
