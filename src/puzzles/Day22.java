package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day22 {
	
	List<Integer> deck1, deck2;
	public Day22() {
		readInput(new File("input/22/input.txt"));
		System.out.println(part1());
		System.out.println(part2());
		
	}
	
	private int part1() {
		Deque<Integer> d1 = new LinkedList<>(deck1);
		Deque<Integer> d2 = new LinkedList<>(deck2);
		
		while (d1.size()>0 && d2.size()>0) {
			int c1 = d1.poll();
			int c2 = d2.poll();
			if (c1>c2) {
				d1.offer(c1);
				d1.offer(c2);
			} else {
				d2.offer(c2);
				d2.offer(c1);
			}
		}
		return Math.max(countScore(d1), countScore(d2));
	}
	
	private int part2() {
		LinkedList<Integer> d1 = new LinkedList<>(deck1);
		LinkedList<Integer> d2 = new LinkedList<>(deck2);
		
		return recursive_Combat(d1, d2) ? countScore(d1) : countScore(d2);
	}
	
	/**
	 * Determines if player 1 wins recursive combat
	 * @param d1 deck player 1
	 * @param d2 deck player 2
	 * @return does player 1 win recursive combat?
	 */
	private boolean recursive_Combat(LinkedList<Integer> d1, LinkedList<Integer> d2) {
		/**
		 * Save encountered states represented by scores to detect cycles
		 */
		Map<Integer,Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		
		while (d1.size()>0 && d2.size()>0) {
			//check for loop
			int count1 = this.countScore(d1);
			int count2 = this.countScore(d2);
			var known = map.getOrDefault(count1, new HashSet<Integer>());
			if (!known.add(count2)) return true; //loop found 
			map.put(count1, known);
			
			int c1 = d1.poll();
			int c2 = d2.poll();
			//do we recurse?
			if (d1.size()>= c1 && d2.size()>=c2) {
				LinkedList<Integer> copy1 = new LinkedList<>(d1.subList(0, c1));
				LinkedList<Integer> copy2 = new LinkedList<>(d2.subList(0, c2));
				if (recursive_Combat(copy1, copy2)) {
					d1.offer(c1);
					d1.offer(c2);
				} else {
					d2.offer(c2);
					d2.offer(c1);
				}
			//no recursion simple combat
			} else {
				if (c1>c2) {
					d1.offer(c1);
					d1.offer(c2);
				} else {
					d2.offer(c2);
					d2.offer(c1);
				}
				
			}
		}
		//return whether player 1 won
		return d1.size()>0;
	}
	
	private int countScore(Deque<Integer> d) {
		int pos = d.size();
		int count = 0;
		var it = d.iterator();
		while (it.hasNext()) {
			count+= pos--*it.next();
		}
		return count;
	}
	
	protected void readInput(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			deck1 = new LinkedList<Integer>();
			deck2 = new LinkedList<Integer>();
			String line;
			//read deck1
			reader.readLine();
			while (!(line=reader.readLine()).isEmpty()) {
				deck1.add(Integer.valueOf(line));
			}
			reader.readLine();
			while ((line=reader.readLine())!=null) {
				deck2.add(Integer.valueOf(line));
			}		
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
