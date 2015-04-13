
public class ThreadExperiment2 extends Thread{
Mailbox mb;

	public static void main(String[] args) {
		Mailbox mailbox = new Mailbox();
		for(int i=0; i<10; i++){
			ThreadExperiment2 t = new ThreadExperiment2(mailbox);
			t.start();
		}
		InfinitePrinter ip = new InfinitePrinter(mailbox);
		ip.start();
		
	}
	
	public ThreadExperiment2(Mailbox mb){
		this.mb=mb;
	}
	public void run(){
		for(int i=0; i<5; i++){
			mb.add(this.getName());
			try {
				this.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(this.getName() + " is done");
	}

}
