
public class InfinitePrinter extends Thread{
Mailbox mb;
public InfinitePrinter(Mailbox mb){
	this.mb = mb;
}

public void run(){
	while(true){
		System.out.println(mb.remove());
	}
}

}
