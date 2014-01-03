import java.io.*;
import java.lang.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

public class Indexer {

	private IndexWriter writer;
	String indexDir ;
	String dataDir ;

	public void run()  {
		long start = System.currentTimeMillis();
		int numIndexed = 0;
		try {
			TextFilesFilter filter = new TextFilesFilter();
			numIndexed = this.index(dataDir, filter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		long end = System.currentTimeMillis();

		System.out.println("Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds");
	}

	public Indexer(String dataDir, String indexDir) throws IOException {
		this.indexDir = indexDir;
		this.dataDir = dataDir;
		try {
			Directory dir = FSDirectory.open(new File(indexDir));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46,
					new StandardAnalyzer(Version.LUCENE_46));
			writer = new IndexWriter(dir, conf); // 3
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try{
			writer.close(); // 4
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int index(String dataDir, FileFilter filter) throws Exception {

		File[] files = new File(dataDir).listFiles();

		for (File f : files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
					&& (filter == null || filter.accept(f))) {
				indexFile(f);
			}
		}
		return writer.numDocs(); // 5
	}

	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase() // 6
					.endsWith(".txt"); // 6
		}
	}

	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new TextField("contents", new FileReader(f))); // 7
		doc.add(new TextField("filename", f.getName(), Field.Store.YES));// 8
		doc.add(new TextField("fullpath", f.getCanonicalPath(), Field.Store.YES));// 9
		return doc;
	}

	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc); // 10
	}
}