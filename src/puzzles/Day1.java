package puzzles;

import java.io.File;
import java.util.HashSet;

import input_reader.IntegerListReader;

public class Day1 {
	int[] input;

	public Day1() {
		super();
		input = IntegerListReader.readIntList(new File("input/1/input.txt"));
		System.out.println("Solution Day 1, Part 1 : " + part1(input, 2020));
		System.out.println("Solution Day 1, Part 2 : " + part2(input, 2020));
	}
	
	/**
	 * Two sum problem O(n)
	 * @return product of two numbers that sum to 2020
	 */
	protected int part1(int[] report, int target) {
		HashSet<Integer> found = new HashSet<Integer>();
		for (int i=0; i<report.length; i++) {
			if (found.contains(target- report[i])) {
				return report[i] * (target -report[i]);
			} else {
				found.add(report[i]);
			}
		}
		return -1;
	}
	/**
	 * Three sum problem O(n^2)
	 * @return product of three numbers that sum to 2020
	 */
	protected int part2(int[] report, int target) {
		HashSet<Integer> found = new HashSet<Integer>();
		found.add(report[0]);
		for (int i=1; i<report.length-1; i++) {
			for (int j=i+1; j< report.length; j++) {
				if (found.contains(target-report[i]-report[j])) {
					return report[i]* report[j] * (target-report[i]-report[j]);
				}
			}
			found.add(report[i]);
		}
		return -1;
	}
	
}
