import java.util.ArrayList;

public class CatMgr {
	ArrayList<String> cats;

	public CatMgr() {
		FileMgr source = new FileMgr("catalog.txt");
		cats = source.getData();
	}

	public ArrayList<String> getCat() {
		return cats;
	}
}
