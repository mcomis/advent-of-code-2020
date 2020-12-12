package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Day07 {
	Map<String,Map<String, Integer>> input;
	
	public Day07() {
		input = readRules(new File("input/7/input.txt"));
		System.out.println("Solution Day 7, Part 1 : " + part1(input, "shiny gold"));
		System.out.println("Solution Day 7, Part 2 : " + part2(input, "shiny gold", 1));
	}
	
	protected int part1(Map<String,Map<String, Integer>> input, String bag) {
		Set<String> validBags = new HashSet<>();
		validBags.add(bag);
		for (int i=0; i< input.keySet().size();i++) {
			int size = validBags.size();
			for ( Entry<String, Map<String, Integer>> e :input.entrySet()) {
				for (String vbag : validBags) {
					if (e.getValue().containsKey(vbag)) {
						validBags.add(e.getKey());
						break;
					}
				}
			}
			//check if we can terminate
			if (validBags.size() == size) break;
		}
		
		return validBags.size()-1;
	}
	
	protected int part2(Map<String,Map<String, Integer>> input, String bag, Integer number) {
		//need to subtract most outer bag(s) as they are assumed to be given.
		return dfs(input, bag, number)-number;
	}
	
	
	protected int dfs(Map<String,Map<String, Integer>> input, String bag, Integer number) {
		int count=0;
		for (Entry<String, Integer> e : input.get(bag).entrySet()) {
			count += dfs(input, e.getKey(), e.getValue());
		}
		return number + number * count;
	}
	
	public  Map<String,Map<String,Integer>> readRules(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			Map<String,Map<String,Integer>> result = new HashMap<String,Map<String,Integer>>();
			while ((line=reader.readLine()) != null) {
					String[] split = line.split("\\s*((bags\\s*contain)|bags.|bags|bag.|bag)\\s*");
					Map<String, Integer> map = new HashMap<String,Integer>();
					for (int i=1; i< split.length; i++) {
						String[] rule = split[i].split("\\s+",2);
						if(!rule[0].equals("no")) {
							map.put(rule[1], Integer.parseInt(rule[0]));
						}
					}
					result.put(split[0],map);				
			}
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
