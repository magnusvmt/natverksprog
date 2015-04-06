import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		String urlString = "http://cs.lth.se/eda095/tentamen/";//Url that you want to grab pdfs from
		int runnerVersion = 5;//What version of Runner would you like?
		DownloadController dc = new DownloadController(urlString);
		
		if(runnerVersion == 1){
			while(true){
				String link = dc.getLinkFromList();
				if(link != null){
					RunnerOne r = new RunnerOne(link);
				}
				else{
					break;
				}
			}
		}
		else if(runnerVersion == 2){
			for(int i=0; i<10; i++){
				RunnerTwo r = new RunnerTwo(dc);
			}
		}
		else if(runnerVersion == 3){
			for(int i=0; i<10; i++){
				new RunnerThree(dc);
			}
		}
		else if(runnerVersion == 4){
			for(int i=0; i<10; i++){
				new RunnerFour(dc);
			}
		}
		else if(runnerVersion == 5){
			ExecutorService executor = Executors.newFixedThreadPool(10);
			for(int i=0; i<10; i++){
				Runnable thread = new RunnerFive(dc);
				executor.execute(thread);
			}
			executor.shutdown();
	        while (!executor.isTerminated()) {
	        }
	        System.out.println("Finished all threads");
		}
		else{
			System.err.println("incorrect runnerversion");
		}
	}

	
}
