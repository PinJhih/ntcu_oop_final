public class Test {
	public static void main(String[] args) {
		ContactMgr mgr = new ContactMgr();

		for (Contact c : mgr.getContacts()) {
			c.print();
		}
	}
}
