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

	public int find(int ID) {
		String id = String.format("%04d", ID);
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).isTarget(1, id))
				return i;
		}
		return -1;
	}

	public void insert(Contact contact) {
		contacts.add(contact);
		save();
	}

	public void update(Contact contact, int index) {
		contacts.remove(index);
		contacts.add(contact);
		Collections.sort(contacts, Contact.ORDER_BY_ID_ASC);
		save();
	}

	public ArrayList<Contact> getContacts() {
		ArrayList<Contact> contacts = new ArrayList<>(this.contacts);
		Collections.sort(contacts, comparator);
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
