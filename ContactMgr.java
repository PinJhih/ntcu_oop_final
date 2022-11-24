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
}
