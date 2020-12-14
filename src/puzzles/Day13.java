package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {
	int currentTime;
	String[] input;
	
	public Day13() {
		readBusPlan(new File("input/13/input.txt"));
		System.out.println("Solution Day 13, Part 1 : " + part1(currentTime, input));
		System.out.println("Solution Day 13, Part 2 : " + part2(input));
	}
	
	protected int part1(int time, String[] busses) {
		int bus = -1;
		int wait = Integer.MAX_VALUE;
		for (String b : busses) {
			if (!b.equals("x")) {
				int freq = Integer.parseInt(b);
				int cwait = (freq - (time%freq))%freq;
				if (wait>cwait) {
					bus=freq;
					wait= cwait;
				}
			}
		}
		return wait * bus;
	}
	
	protected long part2(String[] busses) {
		int[][] input = getModuloEquations(busses);
		//sieving via Chinese remainder theorem
		long x = input[0][1];
		long mod = input[0][0];
		for (int i = 1; i < input.length; i++) {
			while (x % input[i][0] != input[i][1]) {
				x+=mod;
			}
			mod*=input[i][0];
		}
		return x;
	}
	
	/**
	 * Each array [a,b] corresponds to the equation x % a = b
	 * @param busses
	 * @return parameters of modulo equations
	 */
	protected int[][] getModuloEquations(String[] busses){
		int dep=0;
		List<int[]> result = new ArrayList<>();
		for (String b : busses) {
			if (!b.equals("x")) {
				int freq = Integer.parseInt(b);
				int cdep = -dep;
				while(cdep<0) cdep+=freq;
				result.add(new int[] {freq, cdep});			
			}
			dep++;
		}
		int[][] output = new int[result.size()][];
		for (int i=0; i< result.size(); i++) {
			output[i]=result.get(i);
		}
		//sort equations to speed-up sieving
		Arrays.sort(output, (x,y) -> y[0]-x[0]);
		return output;
	}
	
	
	protected void readBusPlan(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			currentTime = Integer.parseInt(reader.readLine().trim());
			input = reader.readLine().split(",");
			reader.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
