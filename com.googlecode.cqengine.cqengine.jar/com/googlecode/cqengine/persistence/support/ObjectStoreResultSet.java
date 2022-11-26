package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;

public class ObjectStoreResultSet extends ResultSet {
   final ObjectStore objectStore;
   final Query query;
   final QueryOptions queryOptions;
   final int retrievalCost;
   final ObjectSet objectSet;

   public ObjectStoreResultSet(ObjectStore objectStore, Query query, QueryOptions queryOptions, int retrievalCost) {
      this.objectStore = objectStore;
      this.query = query;
      this.queryOptions = queryOptions;
      this.retrievalCost = retrievalCost;
      this.objectSet = ObjectSet.fromObjectStore(objectStore, queryOptions);
   }

   public Iterator iterator() {
      return this.objectSet.iterator();
   }

   public boolean contains(Object object) {
      return this.objectStore.contains(object, this.queryOptions);
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }

   public int getRetrievalCost() {
      return this.retrievalCost;
   }

   public int getMergeCost() {
      return this.size();
   }

   public int size() {
      return this.objectStore.size(this.queryOptions);
   }

   public void close() {
      this.objectSet.close();
   }
}
