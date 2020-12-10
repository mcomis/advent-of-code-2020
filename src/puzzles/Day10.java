package puzzles;

import java.io.File;
import java.util.Arrays;

import input_reader.IntegerListReader;

public class Day10 {
	int[] input;
	public Day10() {
		input = IntegerListReader.readIntList(new File("input/10/input.txt"));
		System.out.println("Solution Day 10, Part 1 : " + part1(input));
		System.out.println("Solution Day 10, Part 2 : " + part2(input));
	}
	
	protected int part1(int[] input) {
		Arrays.sort(input);
		int[] count = new int[4];
		int cur = 0;
		for (int i=0; i<input.length; i++) {
			count[input[i]-cur]++;
			cur = input[i];
		}
		//count difference for device
		count[3]++;
		return count[1]*count[3];				
	}
	
	protected long part2(int[] input) {
		Arrays.sort(input);
		long[] dp = new long[input.length];
		//mark adapters that connect to outlet
		for (int i=0; i<3; i++) {
			if (input[i]<=3) dp[i]++;
		}
		for (int i=0; i<input.length; i++) {
			int j=i+1;
			while (j<input.length && (input[j]-input[i]<4)) {
				dp[j]+=dp[i];
				j++;
			}
		}
		return dp[input.length-1];	
	}

}
