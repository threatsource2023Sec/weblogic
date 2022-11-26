package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;

public interface ObjectStore {
   int size(QueryOptions var1);

   boolean contains(Object var1, QueryOptions var2);

   CloseableIterator iterator(QueryOptions var1);

   boolean isEmpty(QueryOptions var1);

   boolean add(Object var1, QueryOptions var2);

   boolean remove(Object var1, QueryOptions var2);

   boolean containsAll(Collection var1, QueryOptions var2);

   boolean addAll(Collection var1, QueryOptions var2);

   boolean retainAll(Collection var1, QueryOptions var2);

   boolean removeAll(Collection var1, QueryOptions var2);

   void clear(QueryOptions var1);
}
