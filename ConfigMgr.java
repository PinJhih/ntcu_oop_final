import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigMgr {
	private String verifyString;
	private int lastID;
	private Map<String, String> sortConfig = new HashMap<>();
	private Map<String, Boolean> displayConfig = new HashMap<>();
	private int showPerPage;
	FileMgr file;

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
		file = new FileMgr("config.txt");
		ArrayList<String> data = file.getData();

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
		showPerPage = Integer.parseInt(perPage.val);
	}

	public String getVerifyString() {
		return verifyString;
	}

	public int getLastID() {
		return lastID;
	}

	public void incLastID() {
		lastID++;
		save();
	}

	public Map<String, String> getSortConfig() {
		return sortConfig;
	}

	public Map<String, Boolean> getDisplayConfig() {
		return displayConfig;
	}

	public int getRowsPerPage() {
		return showPerPage;
	}

	public void setSortByField(String field) {
		sortConfig.put("show_sort_field", field.toLowerCase());
		save();
	}

	public void setSortOrder(String order) {
		sortConfig.put("show_sort_order", order.toLowerCase());
		save();
	}

	public void setShowPerPage(int num) {
		showPerPage = num;
		save();
	}

	public void save() {
		ArrayList<String> data = new ArrayList<>();
		data.add("verify_string:" + verifyString);
		data.add("used_last_id:" + String.format("%04d", lastID));
		data.add("show_sort_field:" + sortConfig.get("show_sort_field"));
		data.add("show_sort_order:" + sortConfig.get("show_sort_order"));
		data.add("show_name:" + displayConfig.get("show_name"));
		data.add("show_phone:" + displayConfig.get("show_phone"));
		data.add("show_catalog:" + displayConfig.get("show_catalog"));
		data.add("show_email:" + displayConfig.get("show_email"));
		data.add("show_birthday:" + displayConfig.get("show_birthday"));
		data.add("show_defalt_perpage:" + showPerPage);
		file.setData(data);
	}
}
