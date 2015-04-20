
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//"http://cs.lth.se/pierre_nugues/"
		Spider spider = new Spider("http://cs.lth.se/pierre_nugues/");
		for(int i=0; i<10;i++){
			Processor p = new Processor(spider);
			p.start();
		}
		while(!spider.isDone()){
			//nothing, this is waiting for the threads to finish. ugly solution
		}
		spider.printResult();
	}

}
