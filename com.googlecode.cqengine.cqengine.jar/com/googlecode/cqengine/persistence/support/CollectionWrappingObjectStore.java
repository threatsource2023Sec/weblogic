package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;
import java.util.Iterator;

public class CollectionWrappingObjectStore implements ObjectStore {
   final Collection backingCollection;

   public CollectionWrappingObjectStore(Collection backingCollection) {
      this.backingCollection = backingCollection;
   }

   public Collection getBackingCollection() {
      return this.backingCollection;
   }

   public int size(QueryOptions queryOptions) {
      return this.backingCollection.size();
   }

   public boolean contains(Object o, QueryOptions queryOptions) {
      return this.backingCollection.contains(o);
   }

   public CloseableIterator iterator(QueryOptions queryOptions) {
      final Iterator i = this.backingCollection.iterator();
      return new CloseableIterator() {
         public void close() {
         }

         public boolean hasNext() {
            return i.hasNext();
         }

         public Object next() {
            return i.next();
         }

         public void remove() {
            i.remove();
         }
      };
   }

   public boolean isEmpty(QueryOptions queryOptions) {
      return this.backingCollection.isEmpty();
   }

   public boolean add(Object object, QueryOptions queryOptions) {
      return this.backingCollection.add(object);
   }

   public boolean remove(Object o, QueryOptions queryOptions) {
      return this.backingCollection.remove(o);
   }

   public boolean containsAll(Collection c, QueryOptions queryOptions) {
      return this.backingCollection.containsAll(c);
   }

   public boolean addAll(Collection c, QueryOptions queryOptions) {
      return this.backingCollection.addAll(c);
   }

   public boolean retainAll(Collection c, QueryOptions queryOptions) {
      return this.backingCollection.retainAll(c);
   }

   public boolean removeAll(Collection c, QueryOptions queryOptions) {
      boolean modified = false;

      Object e;
      for(Iterator var4 = c.iterator(); var4.hasNext(); modified |= this.backingCollection.remove(e)) {
         e = var4.next();
      }

      return modified;
   }

   public void clear(QueryOptions queryOptions) {
      this.backingCollection.clear();
   }
}
