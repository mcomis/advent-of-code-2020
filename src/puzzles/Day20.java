package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import input_reader.CharArrayReader;

public class Day20 {
	Map<Integer, char[][]> input;
	final int _tile_size;
	final int _puzzle_size;
	char[][][][] puzzle;
	int[][] tile_arrangement;
	final char[][] sea_monster;
	
	public Day20() {
		readInput(new File("input/20/input.txt"));
		_tile_size = 10;
		_puzzle_size = (int) Math.sqrt(input.size());
		puzzle = new char[_puzzle_size][_puzzle_size][][];
		tile_arrangement = new int[_puzzle_size][_puzzle_size];
		System.out.println("Solution Day 20, Part 1 : " + part1());
		sea_monster = CharArrayReader.readCharArray(new File("input/20/monster.txt"));
		System.out.println("Solution Day 20, Part 2 : " + part2());
		
	}
	
	protected long part1() {
		dfs(0, 0, new HashSet<Integer>());
		return (long)tile_arrangement[0][0] * (long)tile_arrangement[0][_puzzle_size-1] * (long)tile_arrangement[_puzzle_size-1][0] * (long)tile_arrangement[_puzzle_size-1][_puzzle_size-1];
	}
	
	protected int part2() {
		char[][] _base_image = composeAndTrimImage();
		
		for (char[][] image : getOrientations(_base_image)) {
			if(findMonsters(image) > 0) {
				//assume that there are only monsters in exactly one orientation
// 				printImage(image);
				return countRoughWaters(image);
			}
		}
		return -1;
	}
	
	protected boolean dfs(int row, int col, Set<Integer> visited) {
		if(row == _puzzle_size) {
			//placed all tiles
			return true;
		}
		//determine next free spot 
		int nrow=row;
		int ncol=col+1;
		if (ncol == _puzzle_size) {
			nrow++;
			ncol=0;
		}
		//try to place tile at current spot
		for (var entry : input.entrySet()) {
			if (!visited.contains(entry.getKey())) {
				for (char[][] tile : getOrientations(entry.getValue())) {
					if (doesTilefitAt(row, col, tile)) {
						visited.add(entry.getKey());
						puzzle[row][col]=tile;
						tile_arrangement[row][col]=entry.getKey();
						if (dfs(nrow, ncol, visited)) return true;
						visited.remove(entry.getKey());					
					}
				}
			}
		}
		return false;
	}
	
	protected boolean doesTilefitAt(int row, int col, char[][] tile) {
		for (int i=0; i< _tile_size; i++) {
			for( int j=0; j< _tile_size; j++) {
				if (i==0 && row > 0) {
					//check if fits with tile above
					if (tile[i][j]!=puzzle[row-1][col][_tile_size-1][j]) return false;
				}
				if (j==0 && col>0) {
					//check if fits with tile to the left
					if (tile[i][j]!=puzzle[row][col-1][i][_tile_size-1]) return false;
				}
			}
		}		
		return true;
	}
	
	protected List<char[][]> getOrientations(char[][] tile){
		List<char[][]> result = new ArrayList<char[][]>();
		result.add(tile);
		for (int i=0; i<3; i++) {
			result.add(rotateImage(result.get(result.size()-1)));
		}
		result.add(flipImage(tile));
		for (int i=0; i<3; i++) {
			result.add(rotateImage(result.get(result.size()-1)));
		}
		return result;
	}
	
	protected char[][] flipImage(char[][] tile){
		char[][] result = new char[tile.length][tile.length];		
		for (int i=0; i<tile.length; i++) {
			for (int j=0; j<tile.length; j++) {
				result[i][j]= tile[i][tile.length-1-j];
			}
		}
		return result;
	}
	
	protected char[][] rotateImage(char[][] tile){
		char[][] result = new char[tile.length][tile.length];		
		for (int i=0; i<tile.length; i++) {
			for (int j=0; j<tile.length; j++) {
				result[i][j]= tile[tile.length-1-j][i];
			}
		}
		return result;
	}
	
	protected char[][] composeAndTrimImage(){
		int _new_tile_size = _tile_size-2;
		int width = _puzzle_size * _new_tile_size;
		char[][] result = new char[width][width];
		for (int row=0; row< _puzzle_size; row++) {
			for (int col=0; col< _puzzle_size; col++) {
				for (int i=1; i<_tile_size-1; i++) {
					for (int j=1; j<_tile_size-1; j++) {
						result[row*_new_tile_size+(i-1)][col*_new_tile_size+(j-1)] = puzzle[row][col][i][j];
					}
				}
			}
		}
		return result;
	}
	
	protected int findMonsters(char[][] image) {
		int monsters=0;
		for (int row=0; row<=image.length-sea_monster.length; row++) {
			for (int col=0; col <=image[0].length-sea_monster[0].length; col++) {
				boolean found=true;
				for (int i=0; i<sea_monster.length && found; i++) {
					for (int j=0; j< sea_monster[0].length; j++) {
						if (sea_monster[i][j]=='#' && image[row+i][col+j] != '#') {
							found=false;
							break;
						}
					}
				}
				if (found) {
					monsters++;
					//mark monster
					for (int i=0; i<sea_monster.length; i++) {
						for (int j=0; j< sea_monster[0].length; j++) {
							if (sea_monster[i][j]=='#') {
								 image[row+i][col+j]='O';
							}
						}
					}
					
				}
			}
		}
		return monsters;
	}
	
	protected int countRoughWaters(char[][] image) {
		int count = 0;
		for (int i=0; i< image.length; i++) {
			for (int j=0; j< image[0].length; j++) {
				if (image[i][j]=='#') count ++;
			}
		}
		return count;
	}
	
	protected void readInput(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			input = new HashMap<Integer, char[][]>();
			String line;
			String[] split;
			//read rules
			while ((line=reader.readLine())!=null) {
				split = line.split("[\\s:]");
				input.put(Integer.parseInt(split[1]), readTile(reader));								
			}
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected char[][] readTile(BufferedReader reader) throws IOException{
		List<char[]> lines = new ArrayList<char[]>();
		String line;
		while (!(line=reader.readLine()).isBlank()) {
			lines.add(line.trim().toCharArray());						
		}
		char[][] tile = new char[lines.size()][];
		for (int i=0; i< lines.size(); i++) {
			tile[i]=lines.get(i);
		}
		return tile;		
	}
	
	protected void printImage(char[][] image) {
		for(char[] row : image) {
			System.out.println(Arrays.toString(row).replaceAll("[,\\s\\[\\]]", ""));
		}
	}
	
}
