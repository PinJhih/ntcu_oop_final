import java.util.StringTokenizer;

public class Contact {
	String id, name, phone, cat, email, birthday;

	public Contact(String strFormat) {
		StringTokenizer st = new StringTokenizer(strFormat);
		int ID = Integer.parseInt(st.nextToken());
		id = String.format("%04d", ID);
		name = st.nextToken();
		phone = st.nextToken();
		cat = st.nextToken();
		email = st.nextToken();
		birthday = st.nextToken();
	}

	public String toString() {
		int ID = Integer.parseInt(id);
		String strFormat = String.format("%d %s %s %s %s %s", ID, name, phone, cat, email, birthday);
		return strFormat;
	}

	public void print() {
		System.out.printf("%-4s", id);
		System.out.printf(" %-12s", name);
		System.out.printf(" %-11s", phone);
		System.out.printf(" %-12s", cat);
		System.out.printf(" %-24s", email);
		System.out.printf(" %-4s", birthday);
		System.out.println();
	}
}