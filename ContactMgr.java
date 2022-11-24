import java.util.ArrayList;

public class ContactMgr {
	ArrayList<Contact> contacts = new ArrayList<>();

	public ContactMgr() {
		FileMgr source = new FileMgr("data.txt");
		ArrayList<String> contactsInStr = source.getData();

		for (String contact : contactsInStr) {
			contacts.add(new Contact(contact));
		}
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
		return res;
	}
}
