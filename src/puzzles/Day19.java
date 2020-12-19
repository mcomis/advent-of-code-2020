package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day19 {
	Map<Integer, String> rules;
	List<String> messages;
	
	public Day19() {
		readInput(new File("input/19/input1.txt"));
		System.out.println("Solution Day 19, Part 1 : " + part1());
		readInput(new File("input/19/input2.txt"));
		System.out.println("Solution Day 19, Part 2 : " + part2());
	}
	
	protected int part1() {		
		int count=0;
		String regex = getRegex(0);
		for (String message : messages) {
			if (message.matches(regex)) count++;
		}
		return count;
	}
	
	protected int part2() {
		//substitute cyclic rules
		// rule 8 is is just repetition of 42
		rules.put(8, getRegex(42)+"+");
		//rule 11 is x times rule 42 followed by x times rule 31 (for x>0)
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		//expand pattern up to x=5 repetitions
		for (int i=1; i<5; i++) {
			sb.append("(").append(getRegex(42)).append("{").append(i).append("}").append(getRegex(31)).append("{").append(i).append("})|");
		}
		sb.setLength(sb.length() - 1);
		sb.append(")");
		rules.put(11, sb.toString());
		int count=0;
		String regex = getRegex(0);
		for (String message : messages) {
			if (message.matches(regex)) count++;
		}
		return count;
	}
	
	protected String getRegex(int rule){
		String s = rules.get(rule);
		// check if rule was already converted (no numbers unless its repetitions for part2)
		if (s.matches("\\D*|(.*\\{\\d+\\}.*)")) {
			return s;
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			String[] split = s.split("\\s*\\|\\s*");
			for (String part : split) {
				sb.append("(");
				for (String r : part.split("\\s+")) {
					sb.append(getRegex(Integer.parseInt(r)));
				}
				sb.append(")|");
				
			}
			sb.setLength(sb.length() - 1);
			sb.append(")");
			rules.put(rule, sb.toString());
			return sb.toString();
		}
	}
	
	
	
	protected void readInput(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			rules = new HashMap<Integer, String>();
			String line;
			String[] split;
			//read rules
			while (!(line=reader.readLine()).isBlank()) {
				split = line.split(":\\s");
				rules.put(Integer.parseInt(split[0]), split[1].replace("\"", ""));								
			}
			//read messages
			messages= new ArrayList<String>();
			while ((line=reader.readLine()) != null) {
				messages.add(line.trim());							
			}
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
