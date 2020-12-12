package puzzles;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input_reader.StringListReader;


public class Day12 {
	List<String> input;
	final Map<Character, Integer[]> dir;
	
	public Day12() {
		//initialize directions map
		dir = new HashMap<Character, Integer[]>();
		dir.put('N', new Integer[] {0,1});
		dir.put('S', new Integer[] {0,-1});
		dir.put('E', new Integer[] {1,0});
		dir.put('W', new Integer[] {-1,0});
		dir.put('F', new Integer[] {1,0});
		//solve puzzle
		input = StringListReader.readStringList(new File("input/12/input.txt"));
		System.out.println("Solution Day 12, Part 1 : " + part1(input));
		System.out.println("Solution Day 12, Part 2 : " + part2(input));
	}
	
	protected int part1(List<String> input) {
		int[] pos = {0,0};
		
		for (String s : input) {
			char c = s.charAt(0);
			int val = Integer.parseInt(s.substring(1));
			if (c=='R' || c=='L') {
				if (c=='L') {
					val = 360-val;
				}
				//get current orientation
				Integer[] c_dir = dir.get('F');
				//rotate
				for (int i=0; i< val/90; i++) {
					c_dir = new Integer[]{c_dir[1], - c_dir[0]};
				}
				//update orientation
				dir.put('F', c_dir);
			} else {
				Integer[] cdir= dir.get(c);
				pos[0]+= val*cdir[0];
				pos[1]+= val*cdir[1];
			}
		}	
		return Math.abs(pos[0])+Math.abs(pos[1]);
	}
	
	protected int part2(List<String> input) {
		int[] pos = {0,0};
		Integer[] waypoint = {10,1};
		
		for (String s : input) {
			char c = s.charAt(0);
			int val = Integer.parseInt(s.substring(1));
			if (c=='F') {
				pos[0] += val * waypoint[0];
				pos[1] += val * waypoint[1];
			} else if (c=='R' || c=='L') {
				if (c=='L') {
					val = 360-val;
				}
				//rotate
				for (int i=0; i< val/90; i++) {
					waypoint = new Integer[]{waypoint[1], - waypoint[0]};
				}
			} else {
				Integer[] cdir= dir.get(c);
				waypoint[0]+= val*cdir[0];
				waypoint[1]+= val*cdir[1];
			}
		}	
		return Math.abs(pos[0])+Math.abs(pos[1]);
	}

}
