import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileMgr {
	File file;
	String path;

	public FileMgr(String path) {
		this.path = path;
		this.file = new File(path);
	}

	public ArrayList<String> getData() {
		ArrayList<String> res = new ArrayList<>();
		try {
			FileReader reader = new FileReader(file);
			BufferedReader source = new BufferedReader(reader);
			res = new ArrayList<>();
			for (String l = source.readLine(); l != null; l = source.readLine()) {
				res.add(l);
			}
			source.close();
		} catch (Exception e) {
			System.out.println("Error: Can not read data from " + path);
		}
		return res;
	}

	public void setData(ArrayList<String> data) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(""); // clear file
			for (String line : data)
				writer.append(line + "\n");
			writer.close();
		} catch (Exception e) {
			System.out.println("Error: Can not write data to " + path);
		}
	}
}
