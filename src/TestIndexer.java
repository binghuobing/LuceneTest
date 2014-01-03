import static org.junit.Assert.*;

import org.junit.Test;


public class TestIndexer {

	@Test
	public void test() throws Exception {
		String indexDir = "F:/LuceneIndexDir";
		String dataDir = "F:/LuceneDataDir";

		Indexer indexer = new Indexer(dataDir, indexDir);
		indexer.run();
	}

}
