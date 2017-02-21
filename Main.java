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
	static boolean quit;
	static ArrayList<String> checkedWords = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file
		// If arguments are specified, read/write from/to files instead of Std IO.
	
		if(args.length!=0){
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);	// redirect output to ps
		}else{
			kb = new Scanner(System.in);	// default from Stdin
			ps = System.out;				// default to Stdout
		}
		initialize();

		// TODO methods to read in words, output ladder
		
		while(!quit){
			checkedWords.clear();
			System.out.println("Enter two words separated by a space: ");
			ArrayList<String> startEnd = parse(kb);
			if(!quit){
				String start = startEnd.get(0);
				String end = startEnd.get(1);
				//ArrayList<String> wordLadder = getWordLadderDFS(start, end);
				ArrayList<String> wordLadder = getWordLadderBFS(start, end);
				if(wordLadder.size() > 2){
					printLadder(wordLadder);
				}
				else{
					System.out.println("No word ladder can be found between "+start+" and "+end+".");
				}
			}
		}
	}

	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests. So call it only once at the start of main.
		quit = false;
	}

	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word, rungs, and end word.
	 * If command is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> retList = new ArrayList<String>();
		String input1 = keyboard.next();
		if (input1.equals("/quit")){
			quit = true;
			return null;
		}
		String input2 = keyboard.next();
		if (input2.equals("/quit")){
			quit = true;
			return null;
		}
		
		retList.add(input1.toUpperCase());
		retList.add(input2.toUpperCase());
		return retList;
	}

	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		// Returned list should be ordered start to end. Include start and end.
		// If ladder is empty, return list with just start and end.
		Set<String> dict = makeDictionary();
		ArrayList<String> retPath = new ArrayList<String>();
		List<String> path = new ArrayList<String>();
		path.add(start);
		dfsRecursive(start, end, dict, new Ladder(path, 1, start), retPath);		
		return retPath;
	}
	
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
	   Set<String> dict = makeDictionary();
	   if (!dict.contains(start) || !dict.contains(end)) return new ArrayList<String>();
	   List<String> path = new LinkedList<String>();
	   path.add(start);
	   dict.remove(start);
	   
	   Queue<Ladder> queue = new LinkedList<Ladder>();
	   queue.add(new Ladder(path, 1, start));
	   while (!queue.isEmpty() && !queue.peek().equals(end)){
		   Ladder test = queue.remove();
		   
		   if (end.equals(test.getFinalWord())) return new ArrayList<String>(test.getPath());
		   
		   Iterator<String> i = dict.iterator();
		   while (i.hasNext()){
			   String string = i.next();
			   
			   if (differByOne(string, test.getFinalWord())){
				   List<String> list = new LinkedList<String>(test.getPath());
				   list.add(string);
				   queue.add(new Ladder (list, test.getLength()+1, string));
				   i.remove();
			   }
		   }

	   }
	   if (queue.isEmpty()) return new ArrayList<String>();
	   return new ArrayList<String>(queue.peek().getPath());
		
	}
    
	public static Set<String> makeDictionary() {

		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File("five_letter_words.txt"));
			//infile = new Scanner(new File("short_dict.txt"));
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
		int ladderSize = ladder.size();
		System.out.println("A "+ladderSize+"-rung word ladder exists between "+ladder.get(0)+" and "+ladder.get(ladderSize-1)+".");
		for(String e : ladder){
			System.out.println(e);
		}
	}
	// TODO
	// Other private static methods here
	
	private static void dfsRecursive(String start, String end, Set<String> dict, Ladder ladder, List<String> retPath){
		if(ladder.getFinalWord().equals(end) && retPath.size() == 0){
			for(String e : ladder.getPath()){
				retPath.add(e);
			}
			return;
		}
		
		Iterator<String> i = dict.iterator();
		while(i.hasNext()){
			String next = i.next();
			if(differByOne(next, ladder.getFinalWord()) && !ladder.getPath().contains(next) && !checkedWords.contains(next)){
				List<String> path = ladder.getPath();
				path.add(next);
				checkedWords.add(next);
				dfsRecursive(start, end, dict, new Ladder(path, ladder.getLength()+1, next), retPath);
				path.remove(path.size()-1);
			}
		}
	}
	
	private static boolean differByOne(String str1, String str2){
		if(str1.length() != str2.length()){
			return false;
		}
		int diffCount = 0;
		for(int i = 0; i < str1.length(); i++){
			if(str1.charAt(i) != str2.charAt(i)){
				diffCount++;
			}
		}
		if(diffCount == 1){
			return true;
		}
		else{
			return false;
		}
	}
}
