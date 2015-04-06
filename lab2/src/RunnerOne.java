import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class RunnerOne implements Runnable{
String pdflink;
String name;
	public RunnerOne(String pdflink){
		this.pdflink = pdflink;
		this.name = pdflink.substring(pdflink.lastIndexOf('/') + 1, pdflink.lastIndexOf('.'));
		run();
	}

	public void run(){	
		System.out.println("opening connection");
		URL url;
		try {
			url = new URL(pdflink);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
