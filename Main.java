/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Shahid Hussain Nowshad
 * shn348
 * 16238
 *
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/Cejay/EE422-Project-3-Word-Ladder
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static Set<String> dictSet;
	static ArrayList<Vertex> dictArray;
	static ArrayList<LinkedList<String>> adjacencyList;
	
	static class Vertex{
		boolean discovered;
		String value;
		Vertex parent;
		
		Vertex(String s){
			this.discovered = false;
			this.value = s;
			this.parent = null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		dictSet = makeDictionary();
		dictArray = new ArrayList<Vertex>();
		for (String s : dictSet){
			dictArray.add(new Vertex (s));
		}
		
		adjacencyList = new ArrayList<LinkedList<String>>();
		for (int i = 0; i < dictArray.size(); i ++){
			adjacencyList.add(new LinkedList<String>());
			String node = dictArray.get(i).value;

			for (int j = 0; j < dictSet.size(); j ++){
				String test = dictArray.get(j).value;
				int common = 0;
				for (int k = 0; k < test.length(); k ++){
					if (node.charAt(k) == test.charAt(k)) common ++;
				}
				if (common == 4) {
					adjacencyList.get(i).add(test);
				}
			}
		}
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		return null;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Queue<Vertex> queue = new LinkedList<Vertex>();
		ArrayList<String> ladder = new ArrayList<String>();
		
		// Reset graph
		for (Vertex v : dictArray){
			v.discovered = false;
			v.parent = null;
		}
		
		// Traverse tree and establish parents
		Vertex startVertex = new Vertex(start);
		int startIdx = dictArray.indexOf(startVertex); // if start not in dictionary, startIdx == -1
		dictArray.get(startIdx).discovered = true;
		queue.add(dictArray.get(startIdx));
		
		boolean vertexFound = false;
		while (!queue.isEmpty() && !vertexFound){
			Vertex u = queue.remove();
			if (u.value == end) vertexFound = true;
			
			int idx = dictArray.indexOf(u);
			//for (int i = 0; i < adjacencyList.get(idx).size(); i ++){
			for (String adjacentString : adjacencyList.get(idx)){
				Vertex check = new Vertex (adjacentString);
				int checkIdx = dictArray.indexOf(check);
				if (dictArray.get(checkIdx).discovered == false){
					dictArray.get(checkIdx).discovered = true;
					dictArray.get(checkIdx).parent = u;
					queue.add(dictArray.get(checkIdx));
				}
			}
		}
		
		if (!vertexFound) return ladder;
		else return buildLadderBFS(new Vertex(start), new Vertex(end), ladder);
		//Set<String> dict = makeDictionary();
		
		
		
		//return null; // replace this line later with real return
	}
    
    public static ArrayList<String> buildLadderBFS(Vertex start, Vertex end, ArrayList<String> ladder){
    	if (end.value == start.value){
    		ladder.add(end.value);
    		return ladder;
    	}
    	
    	//int startIdx = dictArray.indexOf(start);
    	int endIdx = dictArray.indexOf(end);
    	
    	if (dictArray.get(endIdx).parent == null) return null;
    	ladder = buildLadderBFS(start, dictArray.get(endIdx).parent, ladder);
    	ladder.add(end.value);
    	return ladder;
    	
    }
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here
}
