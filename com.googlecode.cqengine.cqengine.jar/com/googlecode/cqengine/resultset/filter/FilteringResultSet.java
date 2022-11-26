package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Iterator;

public abstract class FilteringResultSet extends ResultSet {
   final ResultSet wrappedResultSet;
   final Query query;
   final QueryOptions queryOptions;

   public FilteringResultSet(ResultSet wrappedResultSet, Query query, QueryOptions queryOptions) {
      this.wrappedResultSet = wrappedResultSet;
      this.query = query;
      this.queryOptions = queryOptions;
   }

   public Iterator iterator() {
      return new FilteringIterator(this.wrappedResultSet.iterator(), this.queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            return FilteringResultSet.this.isValid(object, queryOptions);
         }
      };
   }

   public boolean contains(Object object) {
      return IteratorUtil.iterableContains(this, object);
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

   public abstract boolean isValid(Object var1, QueryOptions var2);

   public int size() {
      return IteratorUtil.countElements(this);
   }

   public int getRetrievalCost() {
      return this.wrappedResultSet.getRetrievalCost();
   }

   public int getMergeCost() {
      return this.wrappedResultSet.getMergeCost();
   }

   public void close() {
      this.wrappedResultSet.close();
   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }
}
