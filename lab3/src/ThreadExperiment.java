
public class ThreadExperiment extends Thread{


	public static void main(String[] args) {
		for(int i=0; i<10; i++){
			ThreadExperiment t = new ThreadExperiment();
			t.start();
		}
		
	}
	
	public void run(){
		for(int i=0; i<5; i++){
			System.out.println(this.getName());
			try {
				this.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
