import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class PhoneBook {
	private static final Scanner stdin = new Scanner(System.in);
	private static final ConfigMgr config = new ConfigMgr();
	private static final AccountMgr accountMgr = new AccountMgr(config);
	private static final ContactMgr contactMgr = new ContactMgr(config);
	private static final CatMgr catMgr = new CatMgr();

	private static final String[] MAIN_MENU_CONTAIN = { "****************************************",
			"[1].Show_all [2].Show_per_page [3].Show_by_catalog ",
			"[4].Search [5].Modify [6].Delete [7].Add_contact ",
			"[8].Add_catalog [9].Show_all_catalog [10].Set_display_field ",
			"[11].Set_show_perpage [12].Set_order [13].Set_sort_by_field",
			"[14].Show_raw_data [15].Data_optimize [99].Exit_system",
			"****************************************" };

	private static final String[] FIELDS = { "ID", "Name", "Phone", "Catalog", "Email", "BD" };

	private static final String[] MSG_CMD_ERR = { "Error_wrong_command", "Please_enter_again:" };

	private static void print(String str) {
		System.out.print(str);
	}

	private static void println(String str) {
		System.out.println(str);
	}

	private static void login() {
		for (int _t = 0; _t < 3; _t++) {
			print("Account:");
			String acc = stdin.nextLine();
			print("Password:");
			String pw = stdin.nextLine();
			println("Verify_string:" + config.getVerifyString());
			print("Input_Verify_string:");
			String vs = stdin.nextLine();
			if (accountMgr.login(acc, pw, vs)) {
				println("Login_success");
				return;
			}
			println("Error_wrong_account_password_or_verify_string");
		}
		System.exit(0);
	}

	private static void showMainMenu() {
		for (String contain : MAIN_MENU_CONTAIN)
			println(contain);
	}

	private static void subMenu() {
		int codes[] = { 0, 99 };
		String[] options = { "Go_back_to_main_menu", "Exit_system" };
		int cmd = getCmd(options, codes, MSG_CMD_ERR);
		if (cmd == 99)
			System.exit(0);
		System.out.println();
	}

	private static void printOptions(String[] options, int[] codes) {
		for (int i = 0; i < options.length; i++) {
			if (i != 0)
				print(" ");
			System.out.printf("[%d].%s", codes[i], options[i]);
		}
		System.out.println();
	}

	private static boolean isValidCmd(int[] codes, int cmd) {
		for (int code : codes) {
			if (code == cmd)
				return true;
		}
		return false;
	}

	private static int getCmd(String[] options, int[] codes, String[] errorMes) {
		int cmd;
		printOptions(options, codes);
		while (true) {
			try {
				cmd = stdin.nextInt();
				if (isValidCmd(codes, cmd))
					break;
			} catch (Exception e) {
				if (stdin.hasNextLine())
					stdin.nextLine();
			}
			printErrMsg(errorMes);
		}
		return cmd;
	}

	private static void printErrMsg(String[] errorMes) {
		int n = errorMes.length;
		for (int i = 0; i < n; i++) {
			if (i != n - 1)
				println(errorMes[i]);
			else
				print(errorMes[i]);
		}
	}

	private static void showContacts(ArrayList<Contact> contacts) {
		Contact fields = new Contact("[ID] [Name] [Phone] [Catalog] [Email] [BD]");
		fields.print(config.getDisplayConfig());

		for (Contact contact : contacts) {
			contact.print(config.getDisplayConfig());
		}
	}

	private static void showAll() {
		ArrayList<Contact> contacts = contactMgr.getContacts();
		showContacts(contacts);
	}

	private static ArrayList<ArrayList<Contact>> toPages(ArrayList<Contact> contacts, int numPages, int pageSize) {
		ArrayList<ArrayList<Contact>> pages = new ArrayList<>();

		int index = 0;
		for (int i = 0; i < numPages; i++) {
			ArrayList<Contact> page = new ArrayList<>();
			for (int j = 0; j < pageSize && index < contacts.size(); j++, index++) {
				page.add(contacts.get(index));
			}
			pages.add(page);
		}
		return pages;
	}

	private static void showPerPage() {
		String[][] options = { { "Exit" }, { "Exit", "Next Page" }, { "Exit", "Last Page" },
				{ "Exit", "Last Page", "Next Page" }
		};
		int[][] codes = {
				{ 0 }, { 0, 2 }, { 0, 1 }, { 0, 1, 2 }
		};
		ArrayList<Contact> contacts = contactMgr.getContacts();
		int pageSize = config.getRowsPerPage();
		int numPages = Math.ceilDiv(contacts.size(), pageSize);
		ArrayList<ArrayList<Contact>> pages = toPages(contacts, numPages, pageSize);

		int page = 0;
		int cmd;
		boolean terminated = false;
		while (!terminated) {
			System.out.println();
			showContacts(pages.get(page));
			if (numPages == 1)
				cmd = getCmd(options[0], codes[0], MSG_CMD_ERR);
			else if (page == 0)
				cmd = getCmd(options[1], codes[1], MSG_CMD_ERR);
			else if (page == numPages - 1)
				cmd = getCmd(options[2], codes[2], MSG_CMD_ERR);
			else
				cmd = getCmd(options[3], codes[3], MSG_CMD_ERR);

			switch (cmd) {
				case 0:
					terminated = true;
					break;
				case 1:
					page--;
					break;
				case 2:
					page++;
					break;
			}
		}
	}

	private static void showByCat() {
		catMgr.print();
		int index;
		String cat = null;
		while (cat == null) {
			try {
				index = stdin.nextInt();
				cat = catMgr.getCat(index - 1);
				if (cat != null)
					break;
				println("Error_wrong_catalog");
				print("Please_enter_again:");
			} catch (Exception e) {
				println("Error_wrong_catalog");
				print("Please_enter_again:");
				stdin.nextLine();
			}
		}
		showContacts(contactMgr.getContacts(cat));
	}

	private static void search() {
		String[] errMsgs = { "Error_wrong_field", "Please_enter_again:" };
		int[] codes = { 1, 2, 3, 4, 5, 6 };
		int field = getCmd(FIELDS, codes, errMsgs);

		if (stdin.hasNextLine())
			stdin.nextLine();
		String val = stdin.nextLine();
		showContacts(contactMgr.getContacts(field, val));
	}

	private static String selectCat() {
		catMgr.print();
		int index;
		String cat = null;
		while (cat == null) {
			try {
				index = stdin.nextInt();
				cat = catMgr.getCat(index - 1);
				if (cat != null)
					break;
				println("Error_wrong_catalog");
				print("Please_enter_again:");
			} catch (Exception e) {
				println("Error_wrong_catalog");
				print("Please_enter_again:");
				stdin.nextLine();
			}
		}
		return cat;
	}

	private static Contact getContactFromUser(int ID) {
		String name, phone, cat, email, bd;
		print("Name:");
		stdin.nextLine();
		name = stdin.nextLine();
		print("Phone:");
		phone = stdin.nextLine();

		cat = selectCat();

		stdin.nextLine();
		print("Email:");
		email = stdin.nextLine();
		print("Birthday:");
		bd = stdin.nextLine();
		String raw = String.format("%04d %s %s %s %s %s", ID, name, phone, cat, email, bd);
		return new Contact(raw);
	}

	private static void modify() {
		String[] errMsg = { "Error_wrong_ID", "Please_enter_again:" };
		int ID, index;
		while (true) {
			try {
				ID = stdin.nextInt();
				index = contactMgr.find(ID);
				if (index != -1)
					break;
			} catch (Exception e) {
			}
			printErrMsg(errMsg);
		}
		Contact contact = getContactFromUser(ID);
		contactMgr.update(contact, index);
	}

	private static void delete() {
		String[] errMsg = { "Error_wrong_ID", "Please_enter_again:" };
		int ID, index;
		while (true) {
			try {
				ID = stdin.nextInt();
				index = contactMgr.find(ID);
				if (index != -1)
					break;
			} catch (Exception e) {
			}
			printErrMsg(errMsg);
		}
		contactMgr.delete(index);
	}

	private static void add() {
		int ID = config.getLastID() + 1;
		Contact contact = getContactFromUser(ID);
		contactMgr.insert(contact);
		config.incLastID();
	}

	public static void addCat() {
		ArrayList<String> cats = catMgr.getCats();
		String[] errMsg = { "Error_duplicated_catalog", "Please_enter_again:" };
		String cat;
		while (true) {
			if (stdin.hasNextLine())
				stdin.nextLine();
			cat = stdin.nextLine();
			if (!cats.contains(cat))
				break;
			printErrMsg(errMsg);
		}
		catMgr.insert(cat);
	}

	public static void showAllCat() {
		ArrayList<String> cats = catMgr.getCats();
		for (String cat : cats)
			println(cat);
	}

	public static void setDisplayFields() {
		String[] options = { "Yes", "No" };
		int[] codes = { 1, 2 };
		String[] keys = { "show_name",
				"show_phone",
				"show_catalog",
				"show_email",
				"show_birthday"
		};
		Map<String, Boolean> displayConfig = config.getDisplayConfig();

		for (int i = 1; i < FIELDS.length; i++) {
			println(FIELDS[i]);
			int cmd = getCmd(options, codes, MSG_CMD_ERR);
			boolean val = (cmd == 1);
			displayConfig.put(keys[i - 1], val);
		}
		config.setDisplayFields(displayConfig);
	}

	public static void setShowPerPage() {
		int numRows;
		while (true) {
			try {
				numRows = stdin.nextInt();
				if (numRows > 0)
					break;
				println("Error_wrong_number_of_row");
				print("Please_enter_again:");
			} catch (Exception e) {
				println("Error_wrong_number_of_row");
				print("Please_enter_again:");
			}
		}
		config.setShowPerPage(numRows);
	}

	public static void setOrder() {
		String[] options = { "ASC", "DSC" };
		int[] codes = { 1, 2 };
		String[] errMsgs = { "Error_wrong_order", "Please_enter_again:" };
		int order = getCmd(options, codes, errMsgs);
		config.setSortOrder(options[order - 1]);
		contactMgr.updateConfig();
	}

	public static void setSortByField() {
		int[] codes = { 1, 2, 3, 4, 5, 6 };
		String[] errMsgs = { "Error_wrong_field", "Please_enter_again:" };
		int field = getCmd(FIELDS, codes, errMsgs);
		config.setSortByField(FIELDS[field - 1]);
		contactMgr.updateConfig();
	}

	public static void showRawData() {
		ArrayList<String> rawData = contactMgr.getRawData();
		for (String data : rawData) {
			println(data);
		}
	}

	public static void optimize() {
		int[] fieldCodes = { 1, 2, 3, 4, 5, 6 };
		String[] fieldErrMsgs = { "Error_wrong_field", "Please_enter_again:" };
		int field = getCmd(FIELDS, fieldCodes, fieldErrMsgs);

		String[] options = { "ASC", "DSC" };
		int[] orderCodes = { 1, 2 };
		String[] orderErrMsgs = { "Error_wrong_order", "Please_enter_again:" };
		int order = getCmd(options, orderCodes, orderErrMsgs);

		Comparator<Contact> cmp = Contact.getComparator(FIELDS[field - 1], options[order - 1]);
		contactMgr.optimize(cmp);
	}

	public static void main(String[] args) {
		login();
		int cmd = -1;
		boolean cmdErr = false;
		boolean terminated = false;

		while (!terminated) {
			if (!cmdErr)
				showMainMenu();
			try {
				cmd = stdin.nextInt();
				cmdErr = false;
			} catch (Exception e) {
				stdin.nextLine(); // clear input cache
				cmd = -1;
			}

			switch (cmd) {
				case 1:
					showAll();
					break;
				case 2:
					showPerPage();
					break;
				case 3:
					showByCat();
					break;
				case 4:
					search();
					break;
				case 5:
					modify();
					break;
				case 6:
					delete();
					break;
				case 7:
					add();
					break;
				case 8:
					addCat();
					break;
				case 9:
					showAllCat();
					break;
				case 10:
					setDisplayFields();
					break;
				case 11:
					setShowPerPage();
					break;
				case 12:
					setOrder();
					break;
				case 13:
					setSortByField();
					break;
				case 14:
					showRawData();
					break;
				case 15:
					optimize();
					break;
				case 99:
					terminated = true;
					System.exit(0);
					break;
				default:
					printErrMsg(MSG_CMD_ERR);
					cmdErr = true;
					break;
			}
			subMenu();
		}
	}
}
