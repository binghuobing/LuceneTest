import static org.junit.Assert.*;

import org.junit.Test;


public class TestIndexer {

	@Test
	public void test() throws Exception {
		String indexDir = Constants.indexDir;
		String dataDir = Constants.dataDir;

		//Indexer indexer = new Indexer(dataDir, indexDir);
		TikaIndexer indexer = new TikaIndexer(dataDir, indexDir);
		indexer.run();
	}
}
