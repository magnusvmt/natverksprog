import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MCTimeService extends Thread {

	private static ByteArrayInputStream inputStream;

	public MCTimeService() {

	}

	public static void main(String[] args) throws IOException {
		new MCServerOffer().start();
		new MCTimeService().start();

	}

	public void run() {
		try {
			handle();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public void handle() throws IOException {
		DateFormat df = null;
		DatagramSocket serverSocket;
		serverSocket = new DatagramSocket(30000);
		while (true) {
			DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 1024);
			serverSocket.receive(packet);
			inputStream = new ByteArrayInputStream(packet.getData());
			String dataString = new String(packet.getData());
			System.out.println(dataString + ", " + packet.getLength() + ", "
					+ packet.getAddress() + ", " + packet.getPort());
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
			byte[] b = df.format(new Date()).toString().getBytes();
			DatagramPacket out = new DatagramPacket(b, b.length,
					packet.getAddress(), packet.getPort());
			serverSocket.send(out);
		}

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

	public static String readString() throws IOException {
		StringBuilder sb = new StringBuilder();
		char c;
		while (!Character.isWhitespace(c = (char) inputStream.read())) {
			sb.append(c);
		}
		return sb.toString();
	}

}
