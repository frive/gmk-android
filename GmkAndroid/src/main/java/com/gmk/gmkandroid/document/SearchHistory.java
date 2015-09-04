package com.gmk.gmkandroid.document;

import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.UnsavedRevision;

import java.util.Map;

import com.gmk.gmkandroid.util.DateUtils;

public class SearchHistory {
  private static final String DOC_SCHEMA = "search-query";
  private static final String ID_PREFIX = "search:q";
  private static final String DATE_BEGIN = "2000-01-01T00:00:00.000Z";

  public static Query getQuery(Database db) {
    Query q = db.createAllDocumentsQuery();
    q.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);

    //q.setStartKey(ID_PREFIX + ":" + DATE_BEGIN);
    //q.setEndKey(ID_PREFIX + ":" + "\\ufff0");
    q.setDescending(true);

    return q;
  }

  public static Document createSearchQuery(Database db, Map<String, Object> props)
      throws Exception {

    final String now = DateUtils.nowToISOString();
    final String q = props.get("q").toString();

    Document doc = db.getDocument(ID_PREFIX + ":" + q);

    doc.update(new Document.DocumentUpdater() {
      @Override public boolean update(UnsavedRevision newRev) {
        Map<String, Object> oldProps = newRev.getProperties();
        oldProps.put("q", q);
        oldProps.put("schema", DOC_SCHEMA);
        oldProps.put("date", now);

        newRev.setUserProperties(oldProps);

        return true;
      }
    });

    return doc;
  }
}