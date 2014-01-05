import java.io.File;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;

public class TikaIndexer {

	boolean debug = false;
	String dataDir;
	String indexDir;
	IndexWriter writer;

	public TikaIndexer(String dataDir, String indexDir) {
		this.dataDir = dataDir;
		this.indexDir = indexDir;

	}

	public void run() throws Exception {
		long start = new Date().getTime();
		int numIndexed = 0;

		Directory dir = FSDirectory.open(new File(Constants.indexDir));
		writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_46,
				new StandardAnalyzer(Version.LUCENE_46)));
		writer.deleteAll();

		index(new File(dataDir));
		numIndexed = writer.numDocs();
		writer.close();

		long end = new Date().getTime();
		System.out.println("Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds");
	}

	public void index(File direct) throws Exception {
		File[] files = direct.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				index(f);
			} else {
				Document doc = new Document();
				String filename = f.getName();
				String fullpath = f.getCanonicalPath();
				Tika tika = new Tika();
				tika.setMaxStringLength(-1);
				doc.add(new TextField("contents", tika.parse(f)));
				doc.add(new TextField("filename", filename, Field.Store.YES));
				doc.add(new TextField("fullpath", fullpath, Field.Store.YES));
				writer.addDocument(doc);
				
				if (debug)
					System.out.println(fullpath);
			}
		}
	}

}
