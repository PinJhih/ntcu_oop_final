public class Test {
	public static void main(String[] args) {
		Config config = new Config();
		AccountMgr mgr = new AccountMgr(config);
		System.out.println(mgr.login("cis", "134", "12345"));
	}
}
