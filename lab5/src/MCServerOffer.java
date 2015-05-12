import java.net.*;
import java.io.*;

public class MCServerOffer extends Thread {

	public void run() {
		System.out.println("Running server name responder...");
		MulticastSocket ms;
		try {
			ms = new MulticastSocket(33333);
			InetAddress ia = InetAddress.getByName("224.0.0.1");
			ms.joinGroup(ia);
			while (true) {
				//Create separate datagramsocket? Just use ms right now.
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(),0,dp.getLength());
				System.out.println("ServerOffer Received: "+s);			
				if (s.equals("whoareyou")) {
					String resString = InetAddress.getLocalHost().getHostName();
					System.out.println("Responding with: " + resString);
					DatagramPacket res = new DatagramPacket(resString.getBytes(),
							resString.getBytes().length, dp.getAddress(),
							dp.getPort());
					ms.send(res);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
