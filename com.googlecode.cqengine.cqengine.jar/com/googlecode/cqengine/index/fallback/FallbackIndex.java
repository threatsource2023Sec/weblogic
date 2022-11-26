package com.googlecode.cqengine.index.fallback;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.All;
import com.googlecode.cqengine.query.simple.None;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Collections;
import java.util.Iterator;

public class FallbackIndex implements Index {
   private static final int INDEX_RETRIEVAL_COST = Integer.MAX_VALUE;
   private static final int INDEX_MERGE_COST = Integer.MAX_VALUE;
   volatile ObjectStore objectStore = null;

   public boolean isMutable() {
      return true;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return true;
   }

   public boolean isQuantized() {
      return false;
   }

   public ResultSet retrieve(final Query query, final QueryOptions queryOptions) {
      final ObjectSet objectSet = ObjectSet.fromObjectStore(this.objectStore, queryOptions);
      return new ResultSet() {
         public Iterator iterator() {
            if (query instanceof All) {
               return IteratorUtil.wrapAsUnmodifiable(objectSet.iterator());
            } else {
               return (Iterator)(query instanceof None ? Collections.emptyList().iterator() : new FilteringIterator(objectSet.iterator(), queryOptions) {
                  public boolean isValid(Object object, QueryOptions queryOptionsx) {
                     return query.matches(object, queryOptionsx);
                  }
               });
            }
         }

         public boolean contains(Object object) {
            return IteratorUtil.iterableContains(this, object);
         }

         public int size() {
            return IteratorUtil.countElements(this);
         }

         public boolean matches(Object object) {
            return query.matches(object, queryOptions);
         }

         public int getRetrievalCost() {
            return query instanceof None ? 0 : Integer.MAX_VALUE;
         }

         public int getMergeCost() {
            return query instanceof None ? 0 : Integer.MAX_VALUE;
         }

         public void close() {
            objectSet.close();
         }

         public Query getQuery() {
            return query;
         }

         public QueryOptions getQueryOptions() {
            return queryOptions;
         }
      };
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return false;
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return false;
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      this.objectStore = objectStore;
   }

   public void clear(QueryOptions queryOptions) {
      this.objectStore.clear(queryOptions);
   }
}
