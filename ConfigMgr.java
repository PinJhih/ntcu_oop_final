import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigMgr {
	Map<String, String> config = new HashMap<>();
	ArrayList<String> keys = new ArrayList<>(); // 用來記原本的順序
	String[] displayConfigKeys = { "show_name", "show_phone", "show_catalog", "show_email", "show_birthday" };
	FileMgr file;

	public ConfigMgr() {
		file = new FileMgr("config.txt");
		ArrayList<String> data = file.getData();
		for (String d : data) {
			String[] tokens = d.split(" *: *");
			String key = tokens[0], val = tokens[1];
			keys.add(key);
			config.put(key, val);
		}
	}

	public String getVerifyString() {
		return config.get("verify_string");
	}

	public int getLastID() {
		int lastID = Integer.parseInt(config.get("used_last_id"));
		return lastID;
	}

	public Map<String, String> getSortConfig() {
		Map<String, String> displayConfig = new HashMap<>();
		for (String key : keys) {
			if (key.indexOf("show_sort_") != -1) {
				String val = config.get(key);
				displayConfig.put(key, val);
			}
		}
		return displayConfig;
	}

	public Map<String, Boolean> getDisplayConfig() {
		Map<String, Boolean> displayConfig = new HashMap<>();
		for (String key : displayConfigKeys) {
			boolean val = Boolean.parseBoolean(config.get(key));
			displayConfig.put(key, val);
		}
		return displayConfig;
	}

	public void incLastID() {
		int lastID = getLastID() + 1;
		String id = String.valueOf(lastID);
		config.put("used_last_id", id);
		save();
	}

	public int getRowsPerPage() {
		int rowsPerPage = Integer.parseInt(config.get("show_defalt_perpage"));
		return rowsPerPage;
	}

	public void setSortByField(String field) {
		config.put("show_sort_field", field.toLowerCase());
		save();
	}

	public void setSortOrder(String order) {
		config.put("show_sort_order", order.toLowerCase());
		save();
	}

	public void setShowPerPage(int num) {
		String rowsPerPage = String.valueOf(num);
		config.put("show_defalt_perpage", rowsPerPage);
		save();
	}

	public void setDisplayFields(Map<String, Boolean> displayConfig) {
		for (String key : displayConfig.keySet()) {
			String val = String.valueOf(displayConfig.get(key));
			config.put(key, val);
		}
		save();
	}

	public void save() {
		ArrayList<String> data = new ArrayList<>();
		for (String key : keys) {
			String d = String.format("%s: %s", key, config.get(key));
			data.add(d);
		}
		file.setData(data);
	}
}
