package com.googlecode.cqengine.resultset.closeable;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.io.Closeable;
import java.util.Iterator;

public class CloseableResultSet extends ResultSet implements Closeable {
   final ResultSet wrapped;
   final Query query;
   final QueryOptions queryOptions;
   boolean closed = false;

   public CloseableResultSet(ResultSet wrapped, Query query, QueryOptions queryOptions) {
      this.wrapped = wrapped;
      this.query = query;
      this.queryOptions = queryOptions;
   }

   public Iterator iterator() {
      this.ensureNotClosed();
      return this.wrapped.iterator();
   }

   public boolean contains(Object object) {
      this.ensureNotClosed();
      return this.wrapped.contains(object);
   }

   public boolean matches(Object object) {
      this.ensureNotClosed();
      return this.query.matches(object, this.queryOptions);
   }

   public int size() {
      this.ensureNotClosed();
      return this.wrapped.size();
   }

   public int getRetrievalCost() {
      this.ensureNotClosed();
      return this.wrapped.getRetrievalCost();
   }

   public int getMergeCost() {
      this.ensureNotClosed();
      return this.wrapped.getMergeCost();
   }

   public Object uniqueResult() {
      this.ensureNotClosed();
      return this.wrapped.uniqueResult();
   }

   public boolean isEmpty() {
      this.ensureNotClosed();
      return this.wrapped.isEmpty();
   }

   public boolean isNotEmpty() {
      this.ensureNotClosed();
      return this.wrapped.isNotEmpty();
   }

   void ensureNotClosed() {
      if (this.closed) {
         throw new IllegalStateException("ResultSet is closed");
      }
   }

   public void close() {
      this.wrapped.close();
      this.closed = true;
   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }
}
