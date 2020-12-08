package puzzles;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import input_reader.StringIntegerPairListReader;

public class Day8 {
	
	List<Map.Entry<String, Integer>> input;
	public Day8() {
		input = StringIntegerPairListReader.readStringIntegerPairList(new File("input/8/input.txt"));
		System.out.println("Solution Day 8, Part 1 : " + part1(input));
		System.out.println("Solution Day 8, Part 2 : " + part2(input));
	}
	
	public int part1(List<Map.Entry<String, Integer>> instructions) {
		//pointers that define current instruction and value of accumulator
		int[] turtle = new int[] {0,0};
		int[] hare = new int[] {0,0};
		
		//find cycle and its length
		do {
			performInstruction(instructions, turtle);
			performInstruction(instructions, hare);
			performInstruction(instructions, hare);
		} while (turtle[0] != hare[0]);
		
		int cycle_length = hare[1]-turtle[1];
		
		//find beginning of cycle
		hare = new int[] {0,0};
		do {
			performInstruction(instructions, turtle);
			performInstruction(instructions, hare);
		} while (turtle[0] != hare[0]);
		return hare[1] + cycle_length;
	}
	
	public int part2(List<Map.Entry<String, Integer>> instructions) {
		int[] pnt = new int[] {0,0};
		//define possible substitutions of instructions
		Map<String,String> map = new HashMap<String,String>();
		map.put("jmp", "nop");
		map.put("nop", "jmp");
		while(true) {
			Map.Entry<String, Integer> inst= instructions.get(pnt[0]);
			if (inst.getKey().equals("acc")) {
				performInstruction(instructions, pnt);
			} else {
				Map.Entry<String, Integer> new_inst = new AbstractMap.SimpleEntry<String, Integer>(map.get(inst.getKey()), inst.getValue());
				int[] new_pnt = pnt.clone();
				//change current instruction
				instructions.set(new_pnt[0], new_inst);
				//check if program terminates
				Optional<Integer> terminationvalue = runProgram(instructions, new_pnt);
				if (terminationvalue.isPresent()) {
					return terminationvalue.get();
				} else {
					//revert to original instruction and continue
					instructions.set(pnt[0], inst);
					performInstruction(instructions, pnt);
				}
			}
		}
	}
	
	private void performInstruction(List<Map.Entry<String, Integer>> instructions, int[] pnt) {
		Map.Entry<String, Integer> instruction = input.get(pnt[0]);
		if (instruction.getKey().equals("nop")) {
			pnt[0]++;
		} else if (instruction.getKey().equals("acc")) {
			pnt[0]++;
			pnt[1]+=instruction.getValue();
		} else {
			pnt[0]+=instruction.getValue();
		}
	}
	
	/** 
	 * @param pnt
	 * @return final accumulator value or empty if instructions are cyclic
	 */
	private Optional<Integer> runProgram(List<Map.Entry<String, Integer>> instructions, int[] pnt) {
		int[] turtle = pnt.clone();
		int[] hare = pnt.clone();
		
		//find cycle or terminate
		do {
			performInstruction(instructions, turtle);
			performInstruction(instructions, hare);
			if(hare[0]<input.size()) {
				performInstruction(instructions, hare);
			}
		} while (hare[0]<input.size() && turtle[0]!=hare[0]);
		return hare[0]>= input.size() ? Optional.of(hare[1]) : Optional.empty();
	}

}
