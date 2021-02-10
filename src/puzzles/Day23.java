package puzzles;

public class Day23 {
	final String input = "614752839";
	public Day23() {
		System.out.println(part1());
		System.out.println(part2((int) 1e6));
	}
	
	private String part1() {
		//build representation of start configuration
		int[] cups = new int[input.length()+1];
		for (int i=0; i<input.length(); i++) {
			cups[input.charAt(i)-'0']=input.charAt((i+1)%input.length())-'0';
		}
		//play game
		int curr=input.charAt(0)-'0';
		play(cups, curr, 100);
		
		//build output
		StringBuilder sb = new StringBuilder();
		curr=1;
		for (int i=0; i<cups.length-2; i++) {
			sb.append(cups[curr]);
			curr= cups[curr];
		}
		return sb.toString();
	}
	
	private long part2(int num_cups) {
		//build representation of start configuration
		int[] cups = new int[num_cups+1];
		for (int i=0; i<input.length()-1; i++) {
			cups[input.charAt(i)-'0']=input.charAt((i+1)%input.length())-'0';
		}
		cups[input.charAt(input.length()-1)-'0']=input.length()+1;
		for (int i = input.length()+1; i< num_cups; i++) {
			cups[i]=i+1;
		}
		cups[num_cups]=input.charAt(0)-'0';
		
		//play game
		int curr=cups[num_cups];
		play(cups, curr, (int) 1e7);
		
		//compute answer
		int n1= cups[1];
		int n2=cups[n1];
		return (long)n1*(long)n2;
	}
	
	private void play(int[] cups, int curr, int rounds) {
		for (int i=0; i<rounds; i++) {
			curr = move(cups, curr);
		}
	}
	
	/**
	 * perform one rotational move
	 * @param cups representation of cups
	 * @param curr current cup (number)
	 * @return number current cup after rotation
	 */
	private int move(int[] cups, int curr) {
		//remove 3 cups following curr
		int n1 = cups[curr];
		int n2 = cups[n1];
		int n3 = cups[n2];
		cups[curr]=cups[n3];
		
		//determine where removed cups are inserted
		int insert = curr-1;
		while(insert<1 || insert==n1 || insert==n2 || insert==n3) {
			insert--;
			if (insert<1) insert=cups.length-1;		
		}
		
		//insert cups
		cups[n3]=cups[insert];
		cups[insert]=n1;
		
		//return next current cup
		return cups[curr];
	}

}
