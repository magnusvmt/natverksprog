import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	private static ArrayList<ClientHandler> connectedClients;
	private int port = 30000;
	
	public Server(){
		connectedClients = new ArrayList<ClientHandler>();
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

	public void start(){
		try {
			ServerSocket ss = new ServerSocket(port);
			Mailbox messageList = new Mailbox();
			accept(ss, messageList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void accept(ServerSocket ss, Mailbox messageList) throws IOException {
		while (true) {
			Socket socket = null;
			try {
				socket = ss.accept();
				System.out.println("New connection from "
						+ socket.getInetAddress().getHostName() + ":"
						+ socket.getPort() + ". Local port: "
						+ socket.getLocalPort());
				
				ClientHandler ch = new ClientHandler(socket, messageList);
				ch.start();
				connectedClients.add(ch);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	public static synchronized void remove(int clientID){
		for(ClientHandler ch : connectedClients){
			if(ch.getClientID() == clientID){
				connectedClients.remove(ch);
			}
		}
	}
	public static synchronized void remove(ClientHandler ch){
		connectedClients.remove(ch);
	}
	public static synchronized void sendToAll(String s){
		
		for(ClientHandler ch : connectedClients){
			//System.out.println("sending to:" + ch.getClientID());
			ch.writeMessage(s);
		}
	}


}
