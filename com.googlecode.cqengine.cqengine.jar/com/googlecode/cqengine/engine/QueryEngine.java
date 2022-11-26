package com.googlecode.cqengine.engine;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;

public interface QueryEngine {
   ResultSet retrieve(Query var1, QueryOptions var2);

   void addIndex(Index var1, QueryOptions var2);

   Iterable getIndexes();
}
