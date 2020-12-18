package puzzles;

import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import input_reader.StringListReader;

public class Day18 {
	List<String> input;
	
	public Day18() {
		input = StringListReader.readStringList(new File("input/18/input.txt"));
		System.out.println("Solution Day 18, Part 1 : " + part1(input));
		System.out.println("Solution Day 18, Part 2 : " + part2(input));
	}

	private long part1(List<String> formulas) {
		long result = 0L;
		for (String formula: formulas) {
			result += evaluatePostFixFormula(inFixToPostFixNotation(formula, (stack,op) -> stack != '('));
		}
		return result;
	}
	
	private long part2(List<String> formulas) {
		long result = 0L;
		for (String formula: formulas) {
			result += evaluatePostFixFormula(inFixToPostFixNotation(formula, (stack,op)-> stack == '+'));
		}
		return result;
	}
	
	private long evaluatePostFixFormula(List<Character> postfix) {
		Deque<Long> stack = new LinkedList<Long>();
		for (char c : postfix) {
			if (Character.isDigit(c)) {
				stack.push((long) (c-'0'));
			} else {
				long b = stack.pop();
				long a = stack.pop();
				if (c=='+') stack.push(a+b);
				if (c=='*') stack.push(a*b);
			}
		}
		return stack.pop();
	}
	
	private List<Character> inFixToPostFixNotation(String formula, BiPredicate<Character, Character> precedence) {
		List<Character> output = new LinkedList<Character>();
		Deque<Character> operand_stack = new LinkedList<Character>();
		for (char c : formula.toCharArray()) {
			if (c == ' ') continue;
			if (Character.isDigit(c)) {
				output.add(c);
			} else if (c == '(') {
				operand_stack.push(c);
			} else if (c==')') {
				while (operand_stack.peek() != '(') {
					output.add(operand_stack.pop());
				}
				//pop last opening bracket
				operand_stack.pop();
			} else if (c == '+' || c == '*') {
				while (!operand_stack.isEmpty() && precedence.test(operand_stack.peek(),c)) {
					output.add(operand_stack.pop());
				}
				operand_stack.push(c);
			}
			
		}
		while (!operand_stack.isEmpty()) {
			output.add(operand_stack.pop());
		}
		return output;
	}

}
