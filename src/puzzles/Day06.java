package puzzles;

import java.io.File;
import java.util.List;

import input_reader.StringListListReader;

public class Day06 {
	List<List<String>> input;
	public Day06() {
		input = StringListListReader.readStringListList(new File("input/6/input.txt"));
		System.out.println("Solution Day 6, Part 1 : " + part1(input));
		System.out.println("Solution Day 6, Part 2 : " + part2(input));
	}
	
	/**
	 * Represent unique characters in String as integer using binary encoding
	 * @param s
	 * @return integer representing the unique chars in s in binary encoding
	 */
	protected int stringToInt(String s) {
		int result = 0;
		for (char c : s.toCharArray()) {
			result |= (1<<(c-'a'));
		}
		return result;
	}
	
	protected int countOneBits(int num) {
		int count=0;
		while (num>0) {
			count += (num&1);
			num = num>>1;
		}
		return count;
	}
	
	
	protected int part1(List<List<String>> input) {
		int count=0;
		for (List<String> l : input) {
			int group=0;
			for (String s : l) {
				group |= stringToInt(s);
			}
			count+= countOneBits(group);
		}
		return count;
	}
	
	protected int part2(List<List<String>> input) {
		int count=0;
		for (List<String> l : input) {
			int group=Integer.MAX_VALUE;
			for (String s : l) {
				group &= stringToInt(s);
			}
			count+= countOneBits(group);
		}
		return count;
	}
	

}
