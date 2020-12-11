package puzzles;

import java.io.File;

import input_reader.CharArrayReader;

public class Day11 {
	char[][] input;
	final int[][] dir = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1}, {1,-1}};
	public Day11() {
		input = CharArrayReader.readCharArray(new File("input/11/input.txt"));
		System.out.println("Solution Day 11, Part 1 : " + part1(input));
		System.out.println("Solution Day 11, Part 2 : " + part2(input));
	}
	
	protected int part1(char[][] input) {
		int rows = input.length;
		int cols = input[0].length;
		char[][] cur = input.clone();
		char[][] next = new char[rows][cols];
		int change;
		
		do {
		change=0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (cur[r][c]=='L' && countNeighbouringOccupiedSeats(cur, new int[]{r,c})==0) {
					next[r][c]='#';
					change++;
				}
				else if (cur[r][c]=='#' && countNeighbouringOccupiedSeats(cur, new int[]{r,c})>=4) {
					next[r][c]='L';
					change++;
				} else {
				next[r][c] = cur[r][c];
				}
			}
		}
		cur=next;
		next=new char[rows][cols];
		} while (change>0);
		return countTotalOccupiedSeats(cur);
	}
	
	protected int part2(char[][] input) {
		int rows = input.length;
		int cols = input[0].length;
		char[][] cur = input.clone();
		char[][] next = new char[rows][cols];
		int change;
		
		do {
		change=0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (cur[r][c]=='L' && countVisibleOccupiedSeats(cur, new int[]{r,c})==0) {
					next[r][c]='#';
					change++;
				}
				else if (cur[r][c]=='#' && countVisibleOccupiedSeats(cur, new int[]{r,c})>=5) {
					next[r][c]='L';
					change++;
				} else {
				next[r][c] = cur[r][c];
				}
			}
		}
		cur=next;
		next=new char[rows][cols];
		} while (change>0);
		return countTotalOccupiedSeats(cur);
	}
	
	protected int countTotalOccupiedSeats(char[][] input) {
		int count=0;
		for (int r = 0; r < input.length; r++) {
			for (int c = 0; c < input[0].length; c++) {
				if (input[r][c]=='#') count++;
			}
		}
		return count;
		
	}
	
	protected int countNeighbouringOccupiedSeats(char[][] input, int[] pos) {
		int count=0;
		for (int[] d : dir) {
			int[] seat = {pos[0]+d[0], pos[1]+d[1]};
			if (seat[0]>=0 && seat[0]< input.length && seat[1]>=0 && seat[1]< input[0].length && input[seat[0]][seat[1]]=='#') {
				count++;
			}
		}
		return count;
	}
	
	protected int countVisibleOccupiedSeats(char[][] input, int[] pos) {
		int count=0;
		for (int[] d : dir) {
			int[] seat = {pos[0]+d[0], pos[1]+d[1]};
			while (seat[0]>=0 && seat[0]< input.length && seat[1]>=0 && seat[1]< input[0].length && input[seat[0]][seat[1]]=='.') {
				seat[0]+=d[0];
				seat[1]+=d[1];
			}
			if (seat[0]>=0 && seat[0]< input.length && seat[1]>=0 && seat[1]< input[0].length && input[seat[0]][seat[1]]=='#') {
				count++;
			}
		}
		return count;
	}

}
