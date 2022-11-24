import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ContactMgr {
	ArrayList<Contact> contacts = new ArrayList<>();
	Map<String, String> sortConfig;
	Comparator<Contact> comparator;
	ArrayList<String> rawData;

	public ContactMgr(Map<String, String> sortConfig) {
		FileMgr source = new FileMgr("data.txt");
		rawData = source.getData();

		for (String contact : rawData) {
			contacts.add(new Contact(contact));
		}
		this.sortConfig = sortConfig;
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
		String sortField = sortConfig.get("show_sort_field");
		String order = sortConfig.get("show_sort_order");
		comparator = Contact.getComparator(sortField, order);
		Collections.sort(contacts, comparator);
	}
}
