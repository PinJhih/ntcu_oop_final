import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
	private static final Scanner stdin = new Scanner(System.in);
	private static final ConfigMgr config = new ConfigMgr();
	private static final AccountMgr accountMgr = new AccountMgr(config);
	private static final ContactMgr contactMgr = new ContactMgr(config.getSortConfig());
	private static final CatMgr catMgr = new CatMgr();

	private static final String[] mainMenuContain = { "****************************************",
			"[1].Show_all [2].Show_per_page [3].Show_by_catalog ",
			"[4].Search [5].Modify[6].Delete [7].Add_contact ",
			"[8].Add_catalog [9].Show_all_catalog [10].Set_display_field ",
			"[11].Set_show_perpage [12].Set_order [13].Set_sort_by_field",
			"[14].Show_raw_data [15].Data_optimize [99].Exit_system",
			"****************************************" };

	private static final String[] fields = { "ID", "Name", "Phone", "Catalog", "Email", "BD" };

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
		}
		System.exit(0);
	}

	private static void showMainMenu() {
		for (String contain : mainMenuContain)
			println(contain);
	}

	private static void subMenu() {
		println("[0].Go_back_to_main_menu [99].Exit_system");
		int cmd = -1;
		while (cmd != 0) {
			try {
				cmd = stdin.nextInt();
				if (cmd == 99)
					System.exit(0);
				if (cmd == 0)
					break;
				println("Error_wrong_command");
				print("Please_enter_again:");
			} catch (Exception e) {
				println("Error_wrong_command");
				print("Please_enter_again:");
				stdin.nextLine(); // clear
			}
		}
		System.out.println();
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
		subMenu();
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
		subMenu();
	}

	private static void search() {
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				print(" ");
			System.out.printf("[%d].%s", i + 1, fields[i]);
		}
		System.out.println();
		int field = -1;
		while (field == -1) {
			try {
				field = stdin.nextInt();
				if (0 < field && field <= fields.length)
					break;
				println("Error_wrong_field");
				print("Please_enter_again:");
			} catch (Exception e) {
				println("Error_wrong_field");
				print("Please_enter_again:");
			}
		}
		stdin.nextLine();
		String val = stdin.nextLine();
		showContacts(contactMgr.getContacts(field, val));
		subMenu();
	}

	public static void showRawData() {
		ArrayList<String> rawData = contactMgr.getRawData();
		for (String data : rawData) {
			println(data);
		}
		subMenu();
	}

	public static void main(String[] args) {
		// login();
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
				case 2: // TODO: Show_per_page
					break;
				case 3:
					showByCat();
					break;
				case 4:
					search();
					break;
				case 5: // TODO: Modif
					break;
				case 6: // TODO: Delete
					break;
				case 7: // TODO: Add_contact
					break;
				case 8: // TODO: Add_catalog
					break;
				case 9: // TODO: Show_all_catalog
					break;
				case 10: // TODO: Set_display_field
					break;
				case 11: // TODO: Set_show_perpage
					break;
				case 12: // TODO: Set_order
					break;
				case 13: // TODO: Set_sort_by_field
					break;
				case 14:
					showRawData();
					break;
				case 15: // TODO: Data_optimize
					break;
				case 99:
					terminated = true;
					System.exit(0);
					break;
				default:
					println("Error_wrong_command");
					print("Please_enter_again:");
					cmdErr = true;
					break;
			}
		}
	}
}
