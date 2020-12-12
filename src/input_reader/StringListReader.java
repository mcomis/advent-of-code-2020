package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringListReader {

	public StringListReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static  List<String> readStringList(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<String> result = new ArrayList<String>();
			while ((line=reader.readLine()) != null) {
				result.add(line.trim());			
			}
			reader.close();			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
