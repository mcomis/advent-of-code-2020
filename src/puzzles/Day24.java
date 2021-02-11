package puzzles;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import input_reader.StringListReader;

public class Day24 {
	List<String> input;
	static final Map<String, List<Integer>> DECODE_DIRECTION = new HashMap<String, List<Integer>>();
	/**
	 * black tiles represented via doubled coordinates
	 */
	Set<List<Integer>> _black_tiles;
	
	public Day24() {
		DECODE_DIRECTION.put("e", Arrays.asList(new Integer[]{-2,0}));
		DECODE_DIRECTION.put("se", Arrays.asList(new Integer[]{-1,-1}));
		DECODE_DIRECTION.put("sw", Arrays.asList(new Integer[]{1,-1}));
		DECODE_DIRECTION.put("w", Arrays.asList(new Integer[]{2,0}));
		DECODE_DIRECTION.put("nw", Arrays.asList(new Integer[]{1,1}));
		DECODE_DIRECTION.put("ne", Arrays.asList(new Integer[]{-1,1}));
		input = StringListReader.readStringList(new File("input/24/input.txt"));
		System.out.println(part1());
		System.out.println(part2(100));
	}
	
	public int part1() {
		 _black_tiles= new HashSet<>();
		String REGEX = "e|se|sw|w|nw|ne";
		Pattern pattern = Pattern.compile(REGEX);
		
		for (String s : input) {
			//start at reference tile (0,0)
			List<Integer> cur = Arrays.asList(new Integer[] {0,0});
			Matcher matcher = pattern.matcher(s);
			while (matcher.find()) {
				List<Integer> dir = DECODE_DIRECTION.get(matcher.group());
				for (int i=0; i<2; i++) {
					cur.set(i, cur.get(i)+dir.get(i));
				}
			}
			if (_black_tiles.contains(cur)) {
				_black_tiles.remove(cur);
			} else {
				_black_tiles.add(cur);
			}
		}
		return _black_tiles.size();
	}
	
	public long part2(int days) {
		Set<List<Integer>> ntiles;
		for (int i=0; i<days; i++) {
			ntiles=new HashSet<>();
			for (var tile : _black_tiles) {
				//check if black tile lives on
				if(countNeighbours(tile)==1 || countNeighbours(tile)==2) {
					//black tile lives on
					ntiles.add(tile);
				}
				//check if new black tiles are born in adjacent tiles
				for (var dir : DECODE_DIRECTION.values()) {
					List<Integer> nb = new ArrayList<Integer>();
					nb.add(tile.get(0)+dir.get(0));
					nb.add(tile.get(1)+dir.get(1));
					if (!_black_tiles.contains(nb) && countNeighbours(nb)==2) {
						//new black tile
						ntiles.add(nb);
					}
				}				
			}
			_black_tiles = ntiles;			
		}
		return _black_tiles.size();		
	}
	
	/**
	 * count number of black neighboring tiles
	 */
	private int countNeighbours(List<Integer> tile) {
		int answer=0;
		for (var dir : DECODE_DIRECTION.values()) {
			List<Integer> nb = new ArrayList<Integer>();
			nb.add(tile.get(0)+dir.get(0));
			nb.add(tile.get(1)+dir.get(1));
			if(_black_tiles.contains(nb)) {
				answer++;
			}
		}
		return answer;
	}
	
}

