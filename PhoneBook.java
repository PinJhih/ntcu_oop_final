import java.util.Scanner;

public class PhoneBook {
	private static Scanner stdin = new Scanner(System.in);
	private static Config config = new Config();
	private static AccountMgr accountMgr = new AccountMgr(config);
	private static ContactMgr contactMgr = new ContactMgr();
	private static CatMgr catMgr = new CatMgr();

	public static void main(String[] args) {
	}
}