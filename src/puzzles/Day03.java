package puzzles;

import java.io.File;

import input_reader.CharArrayReader;

public class Day03 {
	char[][] input;
	final int[][] slopes = {{1,1},{3,1},{5,1},{7,1},{1,2}};
	
	public Day03() {
		input = CharArrayReader.readCharArray(new File("input/3/input.txt"));
		System.out.println("Solution Day 3, Part 1 : " + part1(input));
		System.out.println("Solution Day 3, Part 2 : " + part2(input, slopes));
	}
	
	protected int traverseSlope(char[][] input, int dx, int dy) {
		int trees=0;
		int x=0,y=0;
		int n=input.length, m=input[0].length;
		while(y<n-dy) {
			x= (x+dx) % m;
			y+=dy;
			if (input[y][x]=='#') trees++;
		}
		return trees;
	}
	
	protected int part1(char[][] input) {
		return traverseSlope(input, 3, 1);
	}
	
	protected long part2(char[][] input, int[][] slopes) {
		long result=1;
		for (int[] slope: slopes) {
			result*= traverseSlope(input, slope[0], slope[1]);
		}
		return result;
	}

}
