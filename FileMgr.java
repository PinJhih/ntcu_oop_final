import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileMgr {
	private ArrayList<String> data;

	public FileMgr(String path) {
		try {
			FileReader reader = new FileReader(path);
			BufferedReader source = new BufferedReader(reader);
			data = new ArrayList<>();
			for (String l = source.readLine(); l != null; l = source.readLine()) {
				data.add(l);
			}
			source.close();
		} catch (Exception e) {
			System.out.println("Error: Can not read data from " + path);
		}
	}

	public ArrayList<String> getData() {
		return data;
	}
}
