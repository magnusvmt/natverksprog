
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import java.util.Set;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Spider {
	private static final boolean DEBUG = true;
	private static final int MAX_PAGES = 2000;
	private Set<String> traversedURLs;
	private ConcurrentLinkedQueue<String> remainingURLs;
	private HashSet<String> emails;
	
	
	public Spider(String url){
		remainingURLs = new ConcurrentLinkedQueue<String>();
		traversedURLs = Collections.synchronizedSet(new HashSet<String>());
		emails = new HashSet<String>();
		remainingURLs.add(url);
	}
	

	public synchronized String nextUrl(){
		while(remainingURLs.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String nextUrl;
		while(true){
			nextUrl = remainingURLs.remove();
			if(!traversedURLs.contains(nextUrl)){
				break;
			}
		}
		traversedURLs.add(nextUrl);
		if(DEBUG)
			System.out.println("Traversed: " + traversedURLs.size() + " Queue size: " + remainingURLs.size() + " to go");
		return nextUrl;
	}

	public synchronized boolean addLink(String s){
		if(traversedURLs.contains(s) || remainingURLs.contains(s)){
			return false;
		}
		remainingURLs.add(s);
		notifyAll();
		return true;
	}
	public synchronized boolean addEmail(String s){
		if(emails.contains(s)){
			return false;
		}
		emails.add(s);
		return true;
	}
	public boolean isDone(){
		//If !remainingURLs.isEmpty() makes some problems because at the start there will only be 1 which thread 0 removes. Then the next wont start.
		if(traversedURLs.size() < MAX_PAGES){
			return false;
		}
		else{
			return true;
		}
	}
	

	
	public void printResult(){
		System.out.println("Traversed Pages:");
		for (String s : traversedURLs){
			System.out.println(s);
		}
		System.out.println("Emails:");
		for (String s : emails){
			System.out.println(s);
		}
	}

}
