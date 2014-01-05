import java.io.*;
import java.lang.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

public class Searcher {
	
	public static void search(String indexDir, String squery) throws Exception{
		Directory dir = FSDirectory.open(new File(indexDir));
		
		QueryParser parser = new QueryParser(Version.LUCENE_46, 
											"contents",
											new StandardAnalyzer(Version.LUCENE_46));
		Query query = parser.parse(squery);
		
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(dir));
		long start = System.currentTimeMillis();
		TopDocs hits = is.search(query, 20);
		long end = System.currentTimeMillis();
		
		System.err.println("Found " + hits.totalHits + 
				" documents (in " +  (end - start) + 
				" milliseconds) that matched query ' " + 
				squery + " ':");
		
		for (ScoreDoc scoreDoc: hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
		}
	}
}
