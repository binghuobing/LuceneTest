import java.io.*;
import java.lang.*;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

public class Searcher {
	String indexDir;
	Directory dir;
	
	public Searcher(String indexDir) throws Exception {
		this.indexDir = indexDir;
		dir = FSDirectory.open(new File(indexDir));
	}
	
	public String search(String field, String squery) throws Exception{
		
		QueryParser parser = new QueryParser(Version.LUCENE_46, 
											field,
											new StandardAnalyzer(Version.LUCENE_46));
		Query query = parser.parse(squery);
		
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(dir));
		long start = System.currentTimeMillis();
		TopDocs hits = is.search(query, 50);
		long end = System.currentTimeMillis();
		
		String ret = "Found " + hits.totalHits + 
				" documents (in " +  (end - start) + 
				" milliseconds) that matched query ' " + 
				squery + " ':\r\n\r\n";
		System.err.println();
		
		for (ScoreDoc scoreDoc: hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
			ret += doc.get("fullpath");
			ret += "\r\n";
		}
		
		return ret;
	}
	
	public String search(String sufix, String field, String squery) throws Exception{
		
		BooleanQuery boolQuery = new BooleanQuery();

		
		
		if (sufix != null) {
			QueryParser parser = new QueryParser(Version.LUCENE_46, Constants.FieldFilename,
					new StandardAnalyzer(Version.LUCENE_46));	
			Query typeSearch = parser.parse(sufix);
			boolQuery.add(typeSearch, Occur.MUST);
		}
		
		QueryParser parser2 = new QueryParser(Version.LUCENE_46, field,
				new StandardAnalyzer(Version.LUCENE_46));	
		Query fieldSearch = parser2.parse(squery);
		boolQuery.add(fieldSearch, Occur.MUST);

		long start = System.currentTimeMillis();
		
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(dir));
		TopDocs hits = is.search(boolQuery, 20);
		
		long end = System.currentTimeMillis();
		
		String ret = "Found " + hits.totalHits + 
				" documents (in " +  (end - start) + 
				" milliseconds) that matched query ' " + 
				squery + " ':\r\n\r\n";
		
		for (ScoreDoc scoreDoc: hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
			ret += doc.get("fullpath");
			ret += "\r\n";
		}
		
		return ret;
	}
}
