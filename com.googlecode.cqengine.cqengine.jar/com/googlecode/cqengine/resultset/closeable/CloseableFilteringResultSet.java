package com.googlecode.cqengine.resultset.closeable;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.filter.FilteringResultSet;
import java.io.Closeable;
import java.util.Iterator;

public abstract class CloseableFilteringResultSet extends FilteringResultSet implements Closeable {
   boolean closed = false;

   public CloseableFilteringResultSet(ResultSet wrappedResultSet, Query query, QueryOptions queryOptions) {
      super(wrappedResultSet, query, queryOptions);
   }

   public Iterator iterator() {
      this.ensureNotClosed();
      return super.iterator();
   }

   public int size() {
      this.ensureNotClosed();
      return super.size();
   }

   public int getRetrievalCost() {
      this.ensureNotClosed();
      return super.getRetrievalCost();
   }

   public int getMergeCost() {
      this.ensureNotClosed();
      return super.getMergeCost();
   }

   public Object uniqueResult() {
      this.ensureNotClosed();
      return super.uniqueResult();
   }

   public boolean isEmpty() {
      this.ensureNotClosed();
      return super.isEmpty();
   }

   public boolean isNotEmpty() {
      this.ensureNotClosed();
      return super.isNotEmpty();
   }

   void ensureNotClosed() {
      if (this.closed) {
         throw new IllegalStateException("ResultSet is closed");
      }
   }

   public void close() {
      super.close();
      this.closed = true;
   }
}
