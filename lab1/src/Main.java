import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1){
			System.out.println("input URL as argument");
			System.exit(1);
		}
		String HTMLPage = "";
		try {
			URL page = new URL(args[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null)
	            sb.append(line);
	        in.close();
	        HTMLPage = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Varf�r anv�nda regex till detta? Finns b�ttre s�tt, blir bara klyddigt.
		Pattern linkPattern = Pattern.compile("<a[^>]+href=[\"']?([^\"'>]+)[\"']?[^>]*>(.+?)</a>",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(HTMLPage);
		ArrayList<String> links = new ArrayList<String>();
		while(pageMatcher.find()){
			String link = pageMatcher.group(1);
		    links.add(link);
		}
		ArrayList<String> endings = new ArrayList<String>();
		for(String s : links){
			//System.out.println(s);
			String extension = s.substring(s.lastIndexOf('.') + 1);
			if(extension.equals("pdf")){
				String name = s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.'));
				try {
					downloadpdf(s,name);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			endings.add(s);
		}
		
	}
	
	private static void downloadpdf(String pdflink, String name) throws IOException{
		System.out.println("opening connection");
		URL url = new URL(pdflink);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(new File(name + ".pdf"));
		System.out.println("reading pdf: " + name);
		int length = -1;
		byte[] buffer = new byte[1024];
		while ((length = in.read(buffer)) > -1) {
		    fos.write(buffer, 0, length);
		}
		fos.close();
		in.close();
		System.out.println(name + " was downloaded");
	}

}
