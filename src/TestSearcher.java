import static org.junit.Assert.*;

import org.junit.Test;


public class TestSearcher {

	@Test
	public void test() {
		String q = "��Ի����ʿ��������";
		try {
			Searcher.search(Constants.indexDir, q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
