package puzzles;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import input_reader.LongListReader;

public class Day09 {
	List<Long> input;
	
	public Day09() {
		input = LongListReader.readLongList(new File("input/9/input.txt"));
		System.out.println("Solution Day 9, Part 1 : " + part1(input, 25));
		System.out.println("Solution Day 9, Part 2 : " + part2(input, part1(input, 25)));
	}
	
	protected long part1(List<Long> input, int preamble) {	
		for (int i=preamble; i< input.size(); i++) {
			if(!twoSum(input, preamble, i)) {
				return input.get(i);
			}
		}
		return -1;
	}
	
	protected long part2(List<Long> input, long goal) {
		int[] idx = rangeSum(input, goal);
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for (int i= idx[0]; i<=idx[1]; i++) {
			min = Math.min(min, input.get(i));
			max = Math.max(max, input.get(i));
		}
		return min + max;
	}
	
	protected boolean twoSum(List<Long> input, int preamble, int index) {
		long goal = input.get(index);
		Set<Long> set = new HashSet<Long>();
		for (int i=index-preamble; i<index; i++) {
			if (set.contains(goal- input.get(i))) return true;
			set.add(input.get(i));
		}
		return false;
	}
	
	protected int[] rangeSum(List<Long> input, long sum) {
		int left=0;
		int right=1;
		long cursum = input.get(left)+input.get(right);
		while (right<input.size()) {
			if (cursum==sum) {
				return new int[] {left,right};
			}
			if (right == left+1 || cursum < sum) {
				right++;
				cursum+=input.get(right);
			} else {
				cursum -= input.get(left);
				left++;
			}			
		}
		return null;
	}

}
