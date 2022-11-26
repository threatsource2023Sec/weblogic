package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import java.util.Collection;

public class FilteredObjectStore implements ObjectStore {
   final ObjectStore backingObjectStore;
   final Query filterQuery;

   public FilteredObjectStore(ObjectStore backingObjectStore, Query filterQuery) {
      this.backingObjectStore = backingObjectStore;
      this.filterQuery = filterQuery;
   }

   public CloseableIterator iterator(QueryOptions queryOptions) {
      final CloseableIterator backingIterator = this.backingObjectStore.iterator(queryOptions);
      final FilteringIterator filteringIterator = new FilteringIterator(backingIterator, queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            return FilteredObjectStore.this.filterQuery.matches(object, queryOptions);
         }
      };
      return new CloseableIterator() {
         public void close() {
            backingIterator.close();
         }

         public boolean hasNext() {
            return filteringIterator.hasNext();
         }

         public Object next() {
            return filteringIterator.next();
         }

         public void remove() {
            throw new UnsupportedOperationException("Modification not supported");
         }
      };
   }

   public int size(QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean contains(Object o, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty(QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean add(Object object, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection c, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c, QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }

   public void clear(QueryOptions queryOptions) {
      throw new UnsupportedOperationException();
   }
}
