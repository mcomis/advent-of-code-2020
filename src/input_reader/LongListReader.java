package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LongListReader {

	
	public static List<Long> readLongList(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<Long> numbers = new ArrayList<Long>();
			while ((line=reader.readLine())!=null) {
				numbers.add(Long.parseLong(line));
			}
			reader.close();			
			return numbers;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
