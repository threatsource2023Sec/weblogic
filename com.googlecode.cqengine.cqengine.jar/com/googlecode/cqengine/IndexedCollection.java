package com.googlecode.cqengine;

import com.googlecode.cqengine.engine.QueryEngine;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Set;

public interface IndexedCollection extends Set, QueryEngine {
   ResultSet retrieve(Query var1);

   ResultSet retrieve(Query var1, QueryOptions var2);

   boolean update(Iterable var1, Iterable var2);

   boolean update(Iterable var1, Iterable var2, QueryOptions var3);

   void addIndex(Index var1);

   void addIndex(Index var1, QueryOptions var2);
}
