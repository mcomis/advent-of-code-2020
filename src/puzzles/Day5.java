package puzzles;

import java.io.File;
import java.util.Arrays;

import input_reader.CharArrayReader;

public class Day5 {
	char[][] input;
	
	public Day5() {
		input = CharArrayReader.readCharArray(new File("input/5/input.txt"));
		System.out.println("Solution Day 5, Part 1 : " + part1(input));
		System.out.println("Solution Day 5, Part 2 : " + part2(input));
	}
	
	public int part1(char[][] input) {
		int max=-1;
		for (char[] seat : input) {
			max = Math.max(max, getSeatID(decodeSeat(seat)));
		}
		return max;
	}
	
	public int part2(char[][] input) {
		int[] seatnumbers = new int[input.length];
		for (int i=0; i< seatnumbers.length; i++) {
			seatnumbers[i]=getSeatID(decodeSeat(input[i]));
		}
		Arrays.sort(seatnumbers);
		int left = 0;
		int right = seatnumbers.length-1;
		while(left+1< right) {
			int mid = left+(right-left)/2;
			if (seatnumbers[mid]-seatnumbers[0]==mid) {
				left=mid;
			} else {
				right=mid;
			}
		}
		return seatnumbers[left]+1;
	}
	
	private int[] decodeSeat(char[] seat) {
		int row=0, col=0;
		for (int i=0; i<7; i++) {
			int bit = seat[i]=='F' ? 0 : 1;
			row= (row<<1)+bit;
		}
		for (int i=7; i<10; i++) {
			int bit = seat[i]=='L' ? 0 : 1;
			col= (col<<1)+bit;
		}
		return new int[] {row, col};
	}
	
	private int getSeatID(int[] seat) {
		return seat[0]*8+seat[1];
	}

}
