package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringListListReader {

	public StringListListReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static  List<List<String>> readStringListList(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<List<String>> result = new ArrayList<>();
			List<String> group = new ArrayList<String>();
			while ((line=reader.readLine()) != null) {
				if (line.isBlank()) {
					result.add(group);			
					group = new ArrayList<String>();
				} else {
					group.add(line.trim());
				}
			}
			result.add(group);
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
