package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntegerListReader {

	
	public static int[] readIntList(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<Integer> numbers = new ArrayList<Integer>();
			while ((line=reader.readLine())!=null) {
				numbers.add(Integer.parseInt(line));
			}
			reader.close();
			int[] result = new int[numbers.size()];
			for (int i=0; i< result.length; i++) {
				result[i]=numbers.get(i);
			}
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
