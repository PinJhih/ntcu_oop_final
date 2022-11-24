import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigMgr {
	private String verifyString;
	private int lastID;
	private Map<String, String> sortConfig = new HashMap<>();
	private Map<String, Boolean> displayConfig = new HashMap<>();
	private int rowsPerPage;

	private class Pair {
		public String key, val;

		public Pair(String key, String val) {
			this.key = key;
			this.val = val;
		}
	}

	private Pair toTokens(String str) {
		String[] tokens = str.split(" *: *");
		return new Pair(tokens[0], tokens[1]);
	}

	public ConfigMgr() {
		FileMgr source = new FileMgr("config.txt");
		ArrayList<String> data = source.getData();

		Pair vs = toTokens(data.get(0));
		Pair ID = toTokens(data.get(1));
		Pair sortField = toTokens(data.get(2));
		Pair sortOrder = toTokens(data.get(3));
		Pair showName = toTokens(data.get(4));
		Pair showPhone = toTokens(data.get(5));
		Pair showCat = toTokens(data.get(6));
		Pair showEmail = toTokens(data.get(7));
		Pair showBirth = toTokens(data.get(8));
		Pair perPage = toTokens(data.get(9));

		verifyString = vs.val;
		lastID = Integer.parseInt(ID.val);
		sortConfig.put(sortField.key, sortField.val);
		sortConfig.put(sortOrder.key, sortOrder.val);
		displayConfig.put(showName.key, Boolean.parseBoolean(showName.val));
		displayConfig.put(showPhone.key, Boolean.parseBoolean(showPhone.val));
		displayConfig.put(showCat.key, Boolean.parseBoolean(showCat.val));
		displayConfig.put(showEmail.key, Boolean.parseBoolean(showEmail.val));
		displayConfig.put(showBirth.key, Boolean.parseBoolean(showBirth.val));
		rowsPerPage = Integer.parseInt(perPage.val);
	}

	public String getVerifyString() {
		return verifyString;
	}

	public int getLastID() {
		return lastID;
	}

	public Map<String, String> getSortConfig() {
		return sortConfig;
	}

	public Map<String, Boolean> getDisplayConfig() {
		return displayConfig;
	}

	public int getRowsPerPage(){
		return rowsPerPage;
	}
}
