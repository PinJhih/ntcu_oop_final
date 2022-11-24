import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountMgr {
	String verifyStr;
	Map<String, String> accounts = new HashMap<>();

	public AccountMgr(ConfigMgr config) {
		FileMgr source = new FileMgr("account.txt");
		ArrayList<String> data = source.getData();

		for (String account : data) {
			String[] tokens = account.split(" ");
			accounts.put(tokens[0], tokens[1]);
		}

		verifyStr = config.getVerifyString();
	}

	public boolean login(String acc, String pw, String vs) {
		if (!vs.equals(verifyStr))
			return false;
		if (!accounts.keySet().contains(acc))
			return false;
		return accounts.get(acc).equals(pw);
	}
}
