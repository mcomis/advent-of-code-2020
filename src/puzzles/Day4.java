package puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Day4 {
		List<Passport> input;
	public Day4() {
		input = readPassword(new File("input/4/input.txt"));
		System.out.println("Solution Day 4, Part 1 : " + part1(input));
		System.out.println("Solution Day 2, Part 2 : " + part2(input));
	}
	
	private int part1(List<Passport> passports) {
		int count=0;
		for (Passport pp : passports) {
			if (pp.containsAllFields()) count++;
		}
		return count;
	}
	
	private int part2(List<Passport> passports) {
		int count=0;
		for (Passport pp : passports) {
			if (pp.isValid()) count++;
		}
		return count;
	}

	public  List<Passport> readPassword(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<Passport> result = new ArrayList<Passport>();
			Passport pp = new Passport();
			while ((line=reader.readLine()) != null) {
				if (line.isBlank()) {
					result.add(pp);			
					pp= new Passport();
				} else {
					String[] split = line.split("[:\\s]+");
					for (int i=0; i<split.length/2; i++) {
						pp.add(split[i*2], split[i*2+1]);
					}
				}
			}
			result.add(pp);
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	protected class Passport{
		String byr, iyr, eyr, hgt, hcl, ecl, pid, cid;
		
		public Passport() {			
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Birth Year: ").append(byr).append("\n");
			sb.append("Issue Year: ").append(iyr).append("\n");
			sb.append("Expiration Year: ").append(eyr).append("\n");
			sb.append("Height: ").append(hgt).append("\n");
			sb.append("Hair Color: ").append(hcl).append("\n");
			sb.append("Eye Color: ").append(ecl).append("\n");
			sb.append("Passport ID: ").append(pid).append("\n");
			return sb.toString();
		}
		
		public boolean add(String key, String value) {
			switch (key) {
			case "byr": 
				this.byr = value;		
				return true;
			case "iyr": 
				this.iyr = value;		
				return true;
			case "eyr": 
				this.eyr = value;		
				return true;
			case "hgt": 
				this.hgt = value;		
				return true;
			case "hcl": 
				this.hcl = value;		
				return true;
			case "ecl": 
				this.ecl = value;		
				return true;
			case "pid": 
				this.pid = value;		
				return true;
			case "cid": 
				this.cid = value;		
				return true;
			default:
				return false;
			}
		}
		
		public boolean containsAllFields() {
			return byr!=null && iyr !=null && eyr !=null && hgt!=null && hcl!=null && ecl!=null && pid!=null;
		}
		
		public boolean isValid() {
			boolean valid = containsAllFields() &&
					byr.matches("^\\d{4}$") && (byr.compareTo("1920")>=0) && (byr.compareTo("2002")<=0) &&
					iyr.matches("^\\d{4}$") && (iyr.compareTo("2010")>=0) && (iyr.compareTo("2020")<=0) &&
					eyr.matches("^\\d{4}$") && (eyr.compareTo("2020")>=0) && (eyr.compareTo("2030")<=0) &&
					((hgt.matches("^\\d+(cm)") && hgt.substring(0, hgt.length()-2).compareTo("150")>=0 && hgt.substring(0, hgt.length()-2).compareTo("193")<=0)
					|| (hgt.matches("^\\d+(in)") && hgt.substring(0, hgt.length()-2).compareTo("59")>=0 && hgt.substring(0, hgt.length()-2).compareTo("76")<=0)) &&
					hcl.matches("^#[\\da-z]{6}$") &&
					ecl.matches("^amb|blu|brn|gry|grn|hzl|oth$") &&
					pid.matches("^\\d{9}$");
			return valid;
		}
	}

}
