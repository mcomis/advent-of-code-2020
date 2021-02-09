package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

public class Day21 {
	List<String[][]> input;
	Set<String> allergens, ingredients;
	/**
	 * map of allergens and ingredients that may potentially contain it
	 */
	
	Map<String, Set<String>> m;
	public Day21() {
		readInput(new File("input/21/input.txt"));
		System.out.println(part1());
		System.out.println(part2());
	}
	
	private int part1() {
		m = new HashMap<String, Set<String>>();
		for (String[][] food : input) {
			for (String a : food[1]) {
				if (!m.containsKey(a)) {
					m.put(a, new HashSet<String>(Arrays.asList(food[0])));
				} else {
					m.get(a).retainAll(Arrays.asList(food[0]));
				}
			}
		}
		Set<String> safe_ingredients = new HashSet<String>(ingredients);
		for (Set<String> v : m.values()) {
			safe_ingredients.removeAll(v);
		}
		
		//compute desired output
		int answer=0;		
		for (String[][] food : input) {
			for (String i : food[0]) {
				if (safe_ingredients.contains(i)) answer++;
			}
		}
		return answer;
	}
	
	private String part2() {
		List<Map.Entry<String, String>> answer = new ArrayList<Entry<String, String>>();
		while (m.size()>0) {
			for (var e : m.entrySet()) {
				if (e.getValue().size()==1) {
					answer.add(new AbstractMap.SimpleEntry<String, String>(e.getKey(), e.getValue().iterator().next()));
					break;
				}
			}
			m.remove(answer.get(answer.size()-1).getKey());
			for (var v : m.values()) {
				v.remove(answer.get(answer.size()-1).getValue());
			}
		}
		//sort ingredients alphabetically
		Collections.sort(answer, (a,b) -> a.getKey().compareTo(b.getKey()));
		return answer.stream().map(p -> String.valueOf(p.getValue())).collect(Collectors.joining(","));
	}
	
	protected void readInput(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			input = new LinkedList<String[][]>();
			String line;
			String[] split;
			//read foods
			while ((line=reader.readLine())!=null) {
				String[][] food = new String[2][];
				split = line.split("(\\(contains\\s)|\\)");
				//ingredients in food
				food[0]=split[0].split("\\s");
				//allergens in food
				food[1]=split[1].split(",\\s");
				input.add(food);
			}
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
		//define set of all ingredients and all allergens
		ingredients = new HashSet<String>();
		allergens = new HashSet<String>();
		for (String[][] food : input) {
			for (String i : food[0]) ingredients.add(i);
			for (String a : food[1]) allergens.add(a);
		}
	}

}
