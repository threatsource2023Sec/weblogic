package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Iterator;

public class DeduplicatingResultSet extends ResultSet {
   final ResultSet wrappedResultSet;
   final Attribute uniqueAttribute;
   final Query query;
   final QueryOptions queryOptions;

   public DeduplicatingResultSet(Attribute uniqueAttribute, ResultSet wrappedResultSet, Query query, QueryOptions queryOptions) {
      this.uniqueAttribute = uniqueAttribute;
      this.wrappedResultSet = wrappedResultSet;
      this.query = query;
      this.queryOptions = queryOptions;
   }

   public Iterator iterator() {
      return new DeduplicatingIterator(this.uniqueAttribute, this.queryOptions, this.wrappedResultSet.iterator());
   }

   public final boolean contains(Object object) {
      return IteratorUtil.iterableContains(this, object);
   }

   public boolean matches(Object object) {
      return this.query.matches(object, this.queryOptions);
   }

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
