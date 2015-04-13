import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler extends Thread{
	int clientID;
	Socket socket;
	Mailbox messageList;
	InputStream is;
	InputStreamReader isr;
	OutputStream os;
	BufferedReader reader;
	PrintWriter writer;
	public ClientHandler(Socket socket, Mailbox messageList){
		 this.socket=socket;
		 this.messageList=messageList;
		 clientID = socket.hashCode(); //Collision possibilities, but this is small scale.
		try {
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			os = socket.getOutputStream();
			reader = new BufferedReader(isr);
			writer = new PrintWriter(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void run() {
		try {
			String line = reader.readLine();
			while( (line != null) && line.compareTo("Q") != 0 ) {
				System.out.println("client " + getClientID() + " sent: " + line);
				if(line.startsWith("M:")){
					Server.sendToAll(line.substring(2));
				}
				else if(line.startsWith("E:")){
					writeMessage(line.substring(2));
				}
				else{
					System.out.println("invalid message");
				}
				line = reader.readLine();
			}
			reader.close();
			
			System.out.println("Socket closed");
		} catch (IOException e) {
			close();
		} 

	}
	
	public int getClientID(){
		return clientID;
	}
	
	private void close(){
		System.out.println("Client " + getClientID() + " disonnected");
		try {
			Server.remove(this);
			isr.close();
			is.close();
			writer.close();
			os.close();  // R�cker nog med att st�nga socketen, men varf�r inte.
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void writeMessage(String msg){
		if(!socket.isConnected()){
			close();
		}
		writer.write(getClientID() + ": " + msg + "\r\n");
		writer.flush();
	}
}
