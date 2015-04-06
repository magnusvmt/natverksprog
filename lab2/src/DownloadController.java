import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DownloadController {
	ArrayList<String> pdfUrlList = new ArrayList<String>();
	
	public DownloadController(String urlString){
		String HTMLPage = "";
		try {
			URL page = new URL(urlString);
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
		Pattern linkPattern = Pattern.compile("<a[^>]+href=[\"']?([^\"'>]+)[\"']?[^>]*>(.+?)</a>",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(HTMLPage);
		ArrayList<String> links = new ArrayList<String>();
		while(pageMatcher.find()){
			String link = pageMatcher.group(1);
		    links.add(link);
		}
		
		for(String s : links){
			String extension = s.substring(s.lastIndexOf('.') + 1);
			if(extension.equals("pdf")){
				pdfUrlList.add(s);
			}
		}
	}
	
	public synchronized String getLinkFromList(){
		if(!pdfUrlList.isEmpty()){
			return pdfUrlList.remove(pdfUrlList.size()-1);
		}
		return null;
	}
}
