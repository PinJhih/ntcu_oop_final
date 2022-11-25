import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ContactMgr {
	ArrayList<Contact> contacts = new ArrayList<>();
	ConfigMgr config;
	Comparator<Contact> comparator;
	ArrayList<String> rawData;
	FileMgr file;

	public ContactMgr(ConfigMgr config) {
		file = new FileMgr("data.txt");
		rawData = file.getData();

		for (String contact : rawData) {
			contacts.add(new Contact(contact));
		}
		this.config = config;
		updateConfig();
	}

	public ArrayList<String> getRawData() {
		return rawData;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public ArrayList<Contact> getContacts(String cat) {
		ArrayList<Contact> res = new ArrayList<>();
		for (Contact contact : contacts) {
			if (contact.isInCat(cat))
				res.add(contact);
		}
		Collections.sort(res, comparator);
		return res;
	}

	public ArrayList<Contact> getContacts(int field, String val) {
		ArrayList<Contact> res = new ArrayList<>();
		for (Contact contact : contacts) {
			if (contact.isTarget(field, val))
				res.add(contact);
		}
		Collections.sort(res, comparator);
		return res;
	}

	public void updateConfig() {
		Map<String, String> sortConfig = config.getSortConfig();
		String sortField = sortConfig.get("show_sort_field");
		String order = sortConfig.get("show_sort_order");
		comparator = Contact.getComparator(sortField, order);
		Collections.sort(contacts, comparator);
	}

	public void save() {
		ArrayList<String> data = new ArrayList<>();
		for (Contact contact : contacts) {
			data.add(contact.toString());
		}
		file.setData(data);
	}
}
