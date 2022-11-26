package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.AbstractSet;
import java.util.Collection;

public class ObjectStoreAsSet extends AbstractSet {
   final ObjectStore objectStore;
   final QueryOptions queryOptions;

   public ObjectStoreAsSet(ObjectStore objectStore, QueryOptions queryOptions) {
      this.objectStore = objectStore;
      this.queryOptions = queryOptions;
   }

   public int size() {
      return this.objectStore.size(this.queryOptions);
   }

   public boolean contains(Object o) {
      return this.objectStore.contains(o, this.queryOptions);
   }

   public CloseableIterator iterator() {
      return this.objectStore.iterator(this.queryOptions);
   }

   public boolean isEmpty() {
      return this.objectStore.isEmpty(this.queryOptions);
   }

   public boolean add(Object object) {
      return this.objectStore.add(object, this.queryOptions);
   }

   public boolean remove(Object o) {
      return this.objectStore.remove(o, this.queryOptions);
   }

   public boolean containsAll(Collection c) {
      return this.objectStore.containsAll(c, this.queryOptions);
   }

   public boolean addAll(Collection c) {
      return this.objectStore.addAll(c, this.queryOptions);
   }

   public boolean retainAll(Collection c) {
      return this.objectStore.retainAll(c, this.queryOptions);
   }

   public boolean removeAll(Collection c) {
      return this.objectStore.removeAll(c, this.queryOptions);
   }

   public void clear() {
      this.objectStore.clear(this.queryOptions);
   }
}
