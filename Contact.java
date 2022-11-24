import java.util.Map;
import java.util.StringTokenizer;

public class Contact {
	String id, name, phone, cat, email, birthday;

	public Contact(String strFormat) {
		StringTokenizer st = new StringTokenizer(strFormat);
		String _id = st.nextToken();
		try {
			int ID = Integer.parseInt(_id);
			id = String.format("%04d", ID);
		} catch (Exception e) {
			id = _id;
		}
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

	public void print(Map<String, Boolean> config) {
		System.out.printf("%-4s", id);
		if (config.get("show_name"))
			System.out.printf(" %-12s", name);
		if (config.get("show_phone"))
			System.out.printf(" %-11s", phone);
		if (config.get("show_catalog"))
			System.out.printf(" %-12s", cat);
		if (config.get("show_email"))
			System.out.printf(" %-24s", email);
		if (config.get("show_birthday"))
			System.out.printf(" %-4s", birthday);
		System.out.println();

	}

	public boolean isInCat(String cat) {
		return this.cat.equals(cat);
	}
}
