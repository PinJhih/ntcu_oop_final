import java.util.Scanner;

public class PhoneBook {
	private static Scanner stdin = new Scanner(System.in);
	private static Config config = new Config();
	private static AccountMgr accountMgr = new AccountMgr(config);
	private static ContactMgr contactMgr = new ContactMgr();
	private static CatMgr catMgr = new CatMgr();

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

	public static void main(String[] args) {
		login();
	}
}
