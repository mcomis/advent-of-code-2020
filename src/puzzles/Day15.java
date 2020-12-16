package puzzles;

import java.util.HashMap;
import java.util.Map;

public class Day15 {
	final int[] input = {16,1,0,18,12,14,19};
	
	public Day15() {		
		System.out.println("Solution Day 15, Part 1 : " + part1_2(input, 2020));
		System.out.println("Solution Day 15, Part 2 : " + part1_2(input, 30000000));
	}
	
	protected int part1_2(int[] input, int rounds) {
		Map<Integer, Integer> map = new HashMap<Integer,Integer>();
		for (int i=0; i< input.length-1; i++) {
			map.put(input[i], i);
		}
		int last = input[input.length-1];
		
		for (int i=input.length; i< rounds; i++) {
			int next = (i-1) - map.getOrDefault(last, i-1);
			map.put(last, i-1);
			last=next;
		}
		return last;
	}

}
