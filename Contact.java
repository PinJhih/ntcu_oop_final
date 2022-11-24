import java.util.Comparator;
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

	public boolean isTarget(int field, String val) {
		if (field == 1)
			return this.id.equals(val);
		if (field == 2)
			return this.name.equals(val);
		if (field == 3)
			return this.phone.equals(val);
		if (field == 4)
			return this.cat.equals(val);
		if (field == 5)
			return this.email.equals(val);
		if (field == 6)
			return this.birthday.equals(val);
		return false;
	}

	public static Comparator<Contact> getComparator(String field, String order) {
		field = field.toUpperCase();
		order = order.toUpperCase();
		if (field.equals("ID")) {
			if (order.equals("ASC")) {
				return ORDER_BY_ID_ASC;
			} else {
				return ORDER_BY_ID_DES;
			}
		}

		if (field.equals("NAME")) {
			if (order.equals("ASC")) {
				return ORDER_BY_NAME_ASC;
			} else {
				return ORDER_BY_NAME_DES;
			}
		}

		if (field.equals("PHONE")) {
			if (order.equals("ASC")) {
				return ORDER_BY_PHONE_ASC;
			} else {
				return ORDER_BY_PHONE_DES;
			}
		}

		if (field.equals("CATALOG")) {
			if (order.equals("ASC")) {
				return ORDER_BY_CAT_ASC;
			} else {
				return ORDER_BY_CAT_DES;
			}
		}

		if (field.equals("EMAIL")) {
			if (order.equals("ASC")) {
				return ORDER_BY_EMAIL_ASC;
			} else {
				return ORDER_BY_EMAIL_DES;
			}
		}

		if (order.equals("ASC")) {
			return ORDER_BY_BD_ASC;
		} else {
			return ORDER_BY_BD_DES;
		}
	}

	public static Comparator<Contact> ORDER_BY_ID_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.id.compareTo(c2.id);
		}
	};

	public static Comparator<Contact> ORDER_BY_ID_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.id.compareTo(c1.id);
		}
	};

	public static Comparator<Contact> ORDER_BY_NAME_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.name.compareTo(c2.name);
		}
	};

	public static Comparator<Contact> ORDER_BY_NAME_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.name.compareTo(c1.name);
		}
	};

	public static Comparator<Contact> ORDER_BY_PHONE_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.phone.compareTo(c2.phone);
		}
	};

	public static Comparator<Contact> ORDER_BY_PHONE_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.phone.compareTo(c1.phone);
		}
	};

	public static Comparator<Contact> ORDER_BY_CAT_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.cat.compareTo(c2.cat);
		}
	};

	public static Comparator<Contact> ORDER_BY_CAT_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.cat.compareTo(c1.cat);
		}
	};

	public static Comparator<Contact> ORDER_BY_EMAIL_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.email.compareTo(c2.email);
		}
	};

	public static Comparator<Contact> ORDER_BY_EMAIL_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.email.compareTo(c1.email);
		}
	};

	public static Comparator<Contact> ORDER_BY_BD_ASC = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.birthday.compareTo(c2.birthday);
		}
	};

	public static Comparator<Contact> ORDER_BY_BD_DES = new Comparator<Contact>() {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c2.birthday.compareTo(c1.birthday);
		}
	};
}
