package com.googlecode.cqengine.engine;

import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;

public interface ModificationListener {
   boolean addAll(ObjectSet var1, QueryOptions var2);

   boolean removeAll(ObjectSet var1, QueryOptions var2);

   void clear(QueryOptions var1);

   void init(ObjectStore var1, QueryOptions var2);
}
