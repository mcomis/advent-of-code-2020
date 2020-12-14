package puzzles;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input_reader.StringListReader;

public class Day14 {
	List<String> input;
	
	public Day14() {
		input = StringListReader.readStringList(new File("input/14/input.txt"));
		System.out.println("Solution Day 14, Part 1 : " + part1(input));
		System.out.println("Solution Day 14, Part 2 : " + part2(input));
	}
	
	protected long part1(List<String> input) {
		Map<Long, Long> memory = new HashMap<Long,Long>();
		String mask="";
		for (String s : input) {
		String[] split = s.split("[=\\[\\]\\s]+");
		if (split.length==2) {
			mask = split[1];
		} else {
			long pos = Long.parseLong(split[1]);
			long val = Long.parseLong(split[2]);
			memory.put(pos, maskV1(mask, val));
		}
		}
		long result = 0;
		for (long l : memory.values()) result+=l;
		return result;
	}
	
	protected long part2(List<String> input) {
		Map<Long, Long> memory = new HashMap<Long,Long>();
		String mask="";
		for (String s : input) {
		String[] split = s.split("[=\\[\\]\\s]+");
		if (split.length==2) {
			mask = split[1];
		} else {
			long pos = Long.parseLong(split[1]);
			long val = Long.parseLong(split[2]);
			for (long l : maskV2(mask, pos)) {
				memory.put(l, val);
			}
		}
		}
		long result = 0;
		for (long l : memory.values()) result+=l;
		return result;
	}
	
	protected long maskV1(String mask, long val) {
		for (int i=0; i< mask.length(); i++) {
			char c = mask.charAt(i);
			long m = (1L<<(mask.length()-i-1));
			if (c=='1') {
				val|=m;
			} else if (c=='0'){
				val&=(~m);
			}
		}
		return val;
	}
	
	protected List<Long> maskV2(String mask, long pos) {
		List<Long> result = new ArrayList<Long>();
		result.add(pos);
		for (int i=0; i< mask.length(); i++) {
			char c = mask.charAt(i);
			long m = (1L<<(mask.length()-i-1));
			if (c=='1') {
				for (int j=0; j< result.size(); j++) {
					result.set(j, result.get(j)|m);
				}
			} else if (c=='X'){
				List<Long> t = new ArrayList<Long>();
				for (long l : result) {
					t.add(l|m);
					t.add(l&(~m));
				}
				result = t;
				
			}
		}
		return result;
	}

}
