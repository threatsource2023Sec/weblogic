package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.query.option.QueryOptions;

public interface SortedKeyStatisticsIndex extends KeyStatisticsIndex {
   CloseableIterable getDistinctKeys(QueryOptions var1);

   CloseableIterable getDistinctKeys(Comparable var1, boolean var2, Comparable var3, boolean var4, QueryOptions var5);

   CloseableIterable getDistinctKeysDescending(QueryOptions var1);

   CloseableIterable getDistinctKeysDescending(Comparable var1, boolean var2, Comparable var3, boolean var4, QueryOptions var5);

   CloseableIterable getStatisticsForDistinctKeysDescending(QueryOptions var1);

   CloseableIterable getKeysAndValues(QueryOptions var1);

   CloseableIterable getKeysAndValues(Comparable var1, boolean var2, Comparable var3, boolean var4, QueryOptions var5);

   CloseableIterable getKeysAndValuesDescending(QueryOptions var1);

   CloseableIterable getKeysAndValuesDescending(Comparable var1, boolean var2, Comparable var3, boolean var4, QueryOptions var5);
}
