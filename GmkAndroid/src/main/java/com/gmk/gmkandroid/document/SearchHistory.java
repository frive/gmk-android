package com.gmk.gmkandroid.document;

import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;

import java.util.Map;

import com.gmk.gmkandroid.util.DateUtils;

public class SearchHistory {
  private static final String DOC_SCHEMA = "search-query";
  private static final String ID_PREFIX = "search:query";
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

    String now = DateUtils.nowToISOString();
    Document doc = db.getDocument("search:query:" + now + ":" + props.get("q"));

    props.put("schema", DOC_SCHEMA);
    props.put("date", now);

    doc.putProperties(props);

    return doc;
  }
}