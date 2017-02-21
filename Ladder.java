package assignment3;

import java.util.List;

public class Ladder{
	private List<String> path;
	private String finalWord;
	private int length;
	
	public Ladder(List<String> path){
		this.path = path;
	}
	
	public Ladder(List<String> path, int length, String finalWord){
		this.path = path;
		this.length = length;
		this.finalWord = finalWord;
	}
	
	public List<String> getPath(){
		return path;
	}
	
	public int getLength(){
		return length;
	}
	
	public String getFinalWord(){
		return finalWord;
	}
	
	public void setPath(List<String> path){
		this.path = path;
	}
	
	public void setLength(int length){
		this.length = length;
	}
}
