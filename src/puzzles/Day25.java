package puzzles;

public class Day25 {
	final long card_public_key = 16616892L;
	final long door_public_key = 14505727;
	public Day25() {
		System.out.println(part1());
		
	}
	
	private long part1() {
		long card_private_key = getPrivateKey(7L, card_public_key);
		return encrypt(1, door_public_key, card_private_key);
	}
	
	
	private long getPrivateKey(long subject_number, long public_key) {
		long m=1L;
		long private_key = 0L;
		while (m!=public_key) {
			m= transform(m, subject_number);
			private_key++;
		}
		return private_key;
	}
	
	private long encrypt(long m, long subject_number, long private_key) {
		for (int i=0; i< private_key; i++) {
			m= transform(m, subject_number);
		}
		return m;
	}
	
	private long transform(long cur, long subject) {
		return  (cur*subject) % 20201227;
	}

}
