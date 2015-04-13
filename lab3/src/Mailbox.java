import java.util.ArrayList;


public class Mailbox {
ArrayList<String> box;
boolean hasData = false;
public Mailbox(){
	box = new ArrayList<String>();
}

public synchronized void add(String s){
	while(hasData){
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println("caught InterruptedException");
		}
	}
	box.add(s);
	hasData=true;
	notifyAll();
}
public synchronized String remove(){
	while(!hasData){
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println("caught InterruptedException");
		}
	}
	hasData=false;
	notifyAll();
	return box.remove(0);
}

}
