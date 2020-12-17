package puzzles;

import java.io.File;

import input_reader.CharArrayReader;

public class Day17 {
	char[][] input;
	final int[] dir = {-1,0,1};
	
	public Day17() {
		input = CharArrayReader.readCharArray(new File("input/17/input.txt"));
		System.out.println("Solution Day 17, Part 1 : " + part1(input,6));
		System.out.println("Solution Day 17, Part 2 : " + part2(input,6));
	}
	
	protected int part1(char[][] input, int repetitions) {
		boolean[][][] state = generateSpace3D(input, repetitions);
		int z = state.length, r = state[0].length, c = state[0][0].length;
		boolean[][][] nextState = new boolean[z][r][c];
		for (int step=0; step< repetitions; step++) {
			for (int i=0; i<z; i++) {
				for (int j=0; j<r; j++) {
					for (int k=0; k<c; k++) {
						int active = countNeighbours3D(state, new int[] {i,j,k});
						if (state[i][j][k] && active >= 2 && active <=3) {
							nextState[i][j][k]=true;
						}
						if ((!state[i][j][k]) && active==3) {
							nextState[i][j][k]=true;
						}
					}
				}
			}
			state=nextState;
			nextState = new boolean[z][r][c];
		}
		return countActiveCubes3D(state);
	}
	
	protected int part2(char[][] input, int repetitions) {
		boolean[][][][] state = generateSpace4D(input, repetitions);
		int d = state.length, z = state[0].length, r = state[0][0].length, c = r = state[0][0][0].length;
		boolean[][][][] nextState = new boolean[d][z][r][c];
		for (int step=0; step< repetitions; step++) {
			for (int h=0; h<d; h++) {
				for (int i=0; i<z; i++) {
					for (int j=0; j<r; j++) {
						for (int k=0; k<c; k++) {
							int active = countNeighbours4D(state, new int[] {h,i,j,k});
							if (state[h][i][j][k] && active >= 2 && active <=3) {
								nextState[h][i][j][k]=true;
							}
							if ((!state[h][i][j][k]) && active==3) {
								nextState[h][i][j][k]=true;
							}
						}
					}
				}
			}
			state=nextState;
			nextState = new boolean[d][z][r][c];
		}
		return countActiveCubes4D(state);
	}
	
	protected int countNeighbours3D(boolean[][][] state, int[] pos) {
		int z = state.length, r = state[0].length, c = state[0][0].length;
		int count=0;
		for (int i : dir) {
			for (int j : dir) {
				for (int k : dir) {
					if (i==0 && j==0 && k==0) continue;
					int[] nb = new int[] {pos[0]+i, pos[1]+j, pos[2]+k};
					if (0<=nb[0] && nb[0] < z && 0 <=nb[1] && nb[1]<r && 0 <= nb[2] && nb[2] < c && state[nb[0]][nb[1]][nb[2]]) count++;
				}
			}
		}
		return count;		
	}
	
	protected int countNeighbours4D(boolean[][][][] state, int[] pos) {
		int d= state.length, z = state[0].length, r = state[0][0].length, c = state[0][0][0].length;
		int count=0;
		for (int i : dir) {
			for (int j : dir) {
				for (int k : dir) {
					for (int l : dir) {
						if (i==0 && j==0 && k==0 && l==0) continue;
						int[] nb = new int[] {pos[0]+i, pos[1]+j, pos[2]+k, pos[3]+l};
						if (0<=nb[0] && nb[0] < d && 0 <=nb[1] && nb[1]<z && 0 <= nb[2] && nb[2] < r && 0 <= nb[3] && nb[3] < c && state[nb[0]][nb[1]][nb[2]][nb[3]]) count++;
					}					
				}
			}
		}
		return count;		
	}
	
	protected int countActiveCubes3D(boolean[][][] state) {
		int z = state.length, r = state[0].length, c = state[0][0].length;
		int count=0;
		for (int i=0; i<z; i++) {
			for (int j=0; j<r; j++) {
				for (int k=0; k<c; k++) {
					if (state[i][j][k]) count++;
				}
			}
		}
		return count;
	}
	
	protected int countActiveCubes4D(boolean[][][][] state) {
		int count=0;
		for (int i=0; i<state.length; i++) count+= countActiveCubes3D(state[i]);
		return count;
	}
	
	boolean[][][] generateSpace3D(char[][] input, int repetitions){
		boolean[][][] result = new boolean[2*repetitions+1][input.length+2*repetitions][input[0].length+ 2*repetitions];
		for (int row=0; row< input.length; row++) {
			for (int col=0; col< input[0].length; col++) {
				result[repetitions][repetitions+row][repetitions+col] = (input[row][col]=='#');
			}
		}
		return result;
	}
	
	boolean[][][][] generateSpace4D(char[][] input, int repetitions){
		boolean[][][][] result = new boolean[2*repetitions+1][2*repetitions+1][input.length+2*repetitions][input[0].length+ 2*repetitions];
		result[repetitions]=generateSpace3D(input, repetitions);
		return result;
	}
 
}
