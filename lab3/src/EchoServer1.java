import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 30000;
		InputStream is = null;
		OutputStream os = null;
		try {
			ServerSocket ss = new ServerSocket(port);
			accept(ss);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void accept(ServerSocket ss) throws IOException {
		Socket socket = null;
		while (true) {
			try {
				socket = ss.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("New connection from "
					+ socket.getInetAddress().getHostName() + ":"
					+ socket.getPort() + ". Local port: "
					+ socket.getLocalPort());

			handleClient(socket);
		}

	}

	public static void handleClient(Socket socket) {
			try {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(isr);
				OutputStream os = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(os);
				String line = reader.readLine();
				while( (line != null) && line.compareTo("exit") != 0 ) {
					System.out.println("client sent: " + line);
					writer.write(line + "\r\n");
					writer.flush();
					line = reader.readLine();
				}
				reader.close();
				isr.close();
				is.close();
				writer.close();
				os.close();
				socket.close();
				System.out.println("Socket closed");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
