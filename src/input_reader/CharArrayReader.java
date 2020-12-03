package input_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharArrayReader {

	public static char[][] readCharArray(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<char[]> input = new ArrayList<char[]>();
			while ((line=reader.readLine())!=null) {
				input.add(line.toCharArray());
			}
			reader.close();
			char[][] result = new char[input.size()][];
			for (int i=0; i< result.length; i++) {
				result[i]=input.get(i);
			}
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
