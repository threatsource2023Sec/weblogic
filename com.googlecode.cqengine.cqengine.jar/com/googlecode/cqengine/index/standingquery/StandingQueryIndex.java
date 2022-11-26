package com.googlecode.cqengine.index.standingquery;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class StandingQueryIndex implements Index, OnHeapTypeIndex {
   private static final int INDEX_RETRIEVAL_COST = 10;
   private final Query standingQuery;
   private final StoredResultSet storedResultSet = new StoredSetBasedResultSet(Collections.newSetFromMap(new ConcurrentHashMap()), 10);

   public StandingQueryIndex(Query standingQuery) {
      this.standingQuery = standingQuery;
   }

   public boolean isMutable() {
      return true;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public Query getStandingQuery() {
      return this.standingQuery;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return this.standingQuery.equals(query);
   }

   public boolean isQuantized() {
      return false;
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      if (!this.supportsQuery(query, queryOptions)) {
         throw new IllegalArgumentException("Unsupported query: " + query);
      } else {
         return this.storedResultSet;
      }
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      this.storedResultSet.clear();
      this.addAll(ObjectSet.fromObjectStore(objectStore, queryOptions), queryOptions);
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         CloseableIterator var4 = objectSet.iterator();

         while(var4.hasNext()) {
            Object object = var4.next();
            if (this.standingQuery.matches(object, queryOptions)) {
               modified |= this.storedResultSet.add(object);
            }
         }

         boolean var9 = modified;
         return var9;
      } finally {
         objectSet.close();
      }
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         CloseableIterator var4 = objectSet.iterator();

         while(var4.hasNext()) {
            Object object = var4.next();
            if (this.standingQuery.matches(object, queryOptions)) {
               modified |= this.storedResultSet.remove(object);
            }
         }

         boolean var9 = modified;
         return var9;
      } finally {
         objectSet.close();
      }
   }

   public void clear(QueryOptions queryOptions) {
      this.storedResultSet.clear();
   }

   public String toString() {
      return "StandingQueryIndex{standingQuery=" + this.standingQuery + '}';
   }

   public static StandingQueryIndex onQuery(Query standingQuery) {
      return new StandingQueryIndex(standingQuery);
   }
}
