package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.query.option.QueryOptions;

public interface KeyStatisticsIndex extends Index {
   CloseableIterable getDistinctKeys(QueryOptions var1);

   Integer getCountForKey(Object var1, QueryOptions var2);

   Integer getCountOfDistinctKeys(QueryOptions var1);

   CloseableIterable getStatisticsForDistinctKeys(QueryOptions var1);

   CloseableIterable getKeysAndValues(QueryOptions var1);
}
