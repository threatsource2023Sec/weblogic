package com.googlecode.cqengine.index;

import com.googlecode.cqengine.engine.ModificationListener;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;

public interface Index extends ModificationListener {
   boolean isMutable();

   boolean supportsQuery(Query var1, QueryOptions var2);

   boolean isQuantized();

   ResultSet retrieve(Query var1, QueryOptions var2);

   Index getEffectiveIndex();
}
