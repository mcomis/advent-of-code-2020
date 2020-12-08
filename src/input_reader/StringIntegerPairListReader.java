package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringIntegerPairListReader {

	public StringIntegerPairListReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static  List<Map.Entry<String, Integer>> readStringIntegerPairList(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<Map.Entry<String, Integer>> result = new ArrayList<>();
			while ((line=reader.readLine()) != null) {
				String[] split = line.split("\\s");
				result.add(new AbstractMap.SimpleEntry<String, Integer>(split[0],Integer.parseInt(split[1].trim())));
			}
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
