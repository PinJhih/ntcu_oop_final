import java.util.ArrayList;

public class CatMgr {
	ArrayList<String> cats;
	FileMgr file;

	public CatMgr() {
		file = new FileMgr("catalog.txt");
		cats = file.getData();
	}

	public ArrayList<String> getCats() {
		return new ArrayList<String>(cats);
	}

	public String getCat(int index) {
		if (index >= cats.size())
			return null;
		return cats.get(index);
	}

	public void insert(String cat) {
		cats.add(cat);
		save();
	}

	public void print() {
		for (int i = 0; i < cats.size(); i++) {
			if (i != 0)
				System.out.print(" ");
			System.out.printf("[%d].%s", i + 1, cats.get(i));
		}
		System.out.println();
	}

	public void save() {
		file.setData(cats);
	}
}
