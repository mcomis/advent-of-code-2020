package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Day16 {
	Map<String, List<Integer[]>> rules;
	int[] myticket;
	List<Integer[]> tickets;
	
	public Day16() {
		readTrainTickets(new File("input/16/input.txt"));
		System.out.println("Solution Day 16, Part 1 : " + part1(tickets));
		System.out.println("Solution Day 16, Part 2 : " + part2(tickets));
	}
	
	protected int part1(List<Integer[]> tickets) {
		int error_rate=0;
		for (Integer[] ticket : tickets) {
			error_rate+= getTicketErrorRate(ticket);
		}
		return error_rate;
	}
	
	protected long part2(List<Integer[]> tickets) {
		//remember possible positions for each rule
		Map<String, Set<Integer>> order = new HashMap<String, Set<Integer>>();
		//initially, each rule can be at each position
		for (String s : rules.keySet()) {
			Set<Integer> positions = new HashSet<Integer>();
			for (int i=0; i< rules.size(); i++) {
				positions.add(i);
			}
			order.put(s, positions);
		}
		
		//reduce possible positions for rules based on given tickets
		for (Integer[] ticket : tickets) {
			//skip invalid tickets
			if (!isTicketValid(ticket)) continue;
			//check all fields on tickets
			for (int i=0; i< ticket.length; i++) {
				//check all rules
				for (var rule : rules.entrySet()) {
					//check if field on the ticket fits with rule					
					boolean fits = false;
					for (Integer[] range : rule.getValue()) {
						if (range[0]<=ticket[i] && range[1]>=ticket[i]) {
							fits=true;
							break;
						}
					}
					if (!fits) {
						//remove position from rules possible set of positions
						order.get(rule.getKey()).remove(i);
					}
				}
			}
		}
		
		//determine unique position of each rule
		boolean changes;
		do {
			changes=false;
			for (var set1 : order.values()) {
				if (set1.size()==1) {
					for (var set2 : order.values()) {
						if (set2.size()>1)
						changes|=set2.remove(set1.iterator().next());
					}
				}
			}
		} while (changes);
		
		//compute result
		long result=1;
		for (String s : order.keySet()) {
			if (s.startsWith("departure")) {
				result*= myticket[order.get(s).iterator().next()];
			}
		}
		return result;
	}
	
	protected int getTicketErrorRate(Integer[] ticket) {
		int error_rate=0;
		for (int field : ticket) {
			if (!isFieldValid(field)) error_rate+=field;
		}
		return error_rate;
	}
	
	protected boolean isTicketValid(Integer[] ticket) {
		for (int field : ticket) {
			if (!isFieldValid(field)){
				return false;
			}
		}
		return true;
	}
	
	protected boolean isFieldValid(int field) {
		for (List<Integer[]> ranges : rules.values()) {
			for (Integer[] range : ranges) {
				if(range[0]<= field && range[1] >= field) return true;
			}
		}
		return false;	
	}
	
	
	protected void readTrainTickets(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			String[] split;
			//read rules
			rules = new HashMap<String, List<Integer[]>>();
			while (!(line=reader.readLine()).isBlank()) {
				split = line.split("(:|-|\\sor\\s)\\s*");
				List<Integer[]> ranges = new ArrayList<Integer[]>();
				for (int i=1; i<=split.length/2; i++) {
					ranges.add(new Integer[] {Integer.parseInt(split[2*i-1]),Integer.parseInt(split[2*i]) });
				}
				rules.put(split[0], ranges);								
			}
			//read ticket
			reader.readLine();
			split = reader.readLine().split("\\s*,\\s*");
			//save number of fields
			int l = split.length;
			myticket = new int[l];
			for (int i = 0; i< l; i++) {
				myticket[i] = Integer.parseInt(split[i]);
			}
			
			//read other tickets
			reader.readLine();
			reader.readLine();
			tickets= new ArrayList<Integer[]>();
			while ((line=reader.readLine()) != null) {
				split = line.split("\\s*,\\s*");
				Integer[] t = new Integer[l];				
				for (int i = 0; i< l; i++) {
					t[i] = Integer.parseInt(split[i]);
				}
				tickets.add(t);							
			}
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
