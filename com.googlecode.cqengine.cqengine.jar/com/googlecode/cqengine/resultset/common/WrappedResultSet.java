package com.googlecode.cqengine.resultset.common;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;

public class WrappedResultSet extends ResultSet {
   protected final ResultSet wrappedResultSet;

   public WrappedResultSet(ResultSet wrappedResultSet) {
      this.wrappedResultSet = wrappedResultSet;
   }

   public Iterator iterator() {
      return this.wrappedResultSet.iterator();
   }

   public boolean contains(Object object) {
      return this.wrappedResultSet.contains(object);
   }

   public boolean matches(Object object) {
      return this.wrappedResultSet.matches(object);
   }

   public int size() {
      return this.wrappedResultSet.size();
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
      return this.wrappedResultSet.getQuery();
   }

   public QueryOptions getQueryOptions() {
      return this.wrappedResultSet.getQueryOptions();
   }
}
