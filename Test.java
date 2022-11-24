public class Test {
	public static void main(String[] args) {
		FileMgr testMgr = new FileMgr("account.txt");
		for (String data : testMgr.getData()) {
			System.out.println(data);
		}

		testMgr = new FileMgr("catalog.txt");
		for (String data : testMgr.getData()) {
			System.out.println(data);
		}

		testMgr = new FileMgr("config.txt");
		for (String data : testMgr.getData()) {
			System.out.println(data);
		}

		testMgr = new FileMgr("data.txt");
		for (String data : testMgr.getData()) {
			System.out.println(data);
		}
	}
}
