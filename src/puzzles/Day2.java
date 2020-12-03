package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
	List<PasswordRiddle> input;
	public Day2() {
		input = readPassword(new File("input/2/input.txt"));
		System.out.println("Solution Day 2, Part 1 : " + part1(input));
		System.out.println("Solution Day 2, Part 2 : " + part2(input));
		
	}
	
	
	protected int part1 (List<PasswordRiddle> riddles) {
		int result=0;
		for (PasswordRiddle riddle : riddles) {
			int count = 0;
			for (int i=0; i< riddle.pw.length(); i++) {
				if (riddle.pw.charAt(i) == riddle.match) count++;
			}
			if (count >= riddle.lb && count<= riddle.ub) result++;
		}
		return result;
	}
	
	protected int part2 (List<PasswordRiddle> riddles) {
		int result=0;
		for (PasswordRiddle riddle : riddles) {
			if ((riddle.pw.charAt(riddle.lb-1) == riddle.match) ^
					(riddle.pw.charAt(riddle.ub-1) == riddle.match)) result++;
		}
		return result;
	}
	
	
	
	public  List<PasswordRiddle> readPassword(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<PasswordRiddle> result = new ArrayList<PasswordRiddle>();
			while ((line=reader.readLine()) != null) {
				String[] split = line.split("[-:\\s]+");
				result.add( new PasswordRiddle(Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2].charAt(0), split[3]));
			}
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected class PasswordRiddle{
		int lb;
		int ub;
		char match;
		String pw;
		
		public PasswordRiddle(int lb, int ub, char match, String pw) {
			super();
			this.lb = lb;
			this.ub = ub;
			this.match = match;
			this.pw = pw;
		}		
	}

}
