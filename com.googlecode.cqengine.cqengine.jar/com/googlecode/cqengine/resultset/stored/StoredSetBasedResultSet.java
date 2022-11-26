package com.googlecode.cqengine.resultset.stored;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Iterator;
import java.util.Set;

public class StoredSetBasedResultSet extends StoredResultSet {
   private final Set backingSet;
   private final int retrievalCost;

   public StoredSetBasedResultSet(Set backingSet) {
      this(backingSet, 0);
   }

   public StoredSetBasedResultSet(Set backingSet, int retrievalCost) {
      this.backingSet = backingSet;
      this.retrievalCost = retrievalCost;
   }

   public boolean contains(Object o) {
      return this.backingSet.contains(o);
   }

   public boolean matches(Object object) {
      return this.contains(object);
   }

   public Iterator iterator() {
      return this.backingSet.iterator();
   }

   public boolean add(Object o) {
      return this.backingSet.add(o);
   }

   public boolean remove(Object o) {
      return this.backingSet.remove(o);
   }

   public boolean isEmpty() {
      return this.backingSet.isEmpty();
   }

   public boolean isNotEmpty() {
      return !this.backingSet.isEmpty();
   }

   public void clear() {
      this.backingSet.clear();
   }

   public int size() {
      return this.backingSet.size();
   }

   public int getRetrievalCost() {
      return this.retrievalCost;
   }

   public int getMergeCost() {
      return this.backingSet.size();
   }

   public void close() {
   }

   public Query getQuery() {
      throw new UnsupportedOperationException();
   }

   public QueryOptions getQueryOptions() {
      throw new UnsupportedOperationException();
   }
}
