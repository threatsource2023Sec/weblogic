package com.googlecode.cqengine;

import com.googlecode.cqengine.engine.CollectionQueryEngine;
import com.googlecode.cqengine.engine.QueryEngineInternal;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.CloseableRequestResources;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.onheap.OnHeapPersistence;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.persistence.support.ObjectStoreAsSet;
import com.googlecode.cqengine.persistence.support.PersistenceFlags;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.closeable.CloseableResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ConcurrentIndexedCollection implements IndexedCollection {
   protected final Persistence persistence;
   protected final ObjectStore objectStore;
   protected final QueryEngineInternal indexEngine;

   public ConcurrentIndexedCollection() {
      this(OnHeapPersistence.withoutPrimaryKey());
   }

   public ConcurrentIndexedCollection(Persistence persistence) {
      this.persistence = persistence;
      this.objectStore = persistence.createObjectStore();
      QueryEngineInternal queryEngine = new CollectionQueryEngine();
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      try {
         queryEngine.init(this.objectStore, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      this.indexEngine = queryEngine;
   }

   public ResultSet retrieve(Query query) {
      final QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);
      flagAsReadRequest(queryOptions);
      ResultSet results = this.indexEngine.retrieve(query, queryOptions);
      return new CloseableResultSet(results, query, queryOptions) {
         public void close() {
            super.close();
            ConcurrentIndexedCollection.this.closeRequestScopeResourcesIfNecessary(queryOptions);
         }
      };
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      final QueryOptions queryOptionsOpened = this.openRequestScopeResourcesIfNecessary(queryOptions);
      flagAsReadRequest(queryOptionsOpened);
      ResultSet results = this.indexEngine.retrieve(query, queryOptions);
      return new CloseableResultSet(results, query, queryOptions) {
         public void close() {
            super.close();
            ConcurrentIndexedCollection.this.closeRequestScopeResourcesIfNecessary(queryOptionsOpened);
         }
      };
   }

   public boolean update(Iterable objectsToRemove, Iterable objectsToAdd) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var4;
      try {
         var4 = this.update(objectsToRemove, objectsToAdd, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var4;
   }

   public boolean update(Iterable objectsToRemove, Iterable objectsToAdd, QueryOptions queryOptions) {
      queryOptions = this.openRequestScopeResourcesIfNecessary(queryOptions);

      boolean var5;
      try {
         boolean modified = this.doRemoveAll(objectsToRemove, queryOptions);
         var5 = this.doAddAll(objectsToAdd, queryOptions) || modified;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var5;
   }

   public void addIndex(Index index) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      try {
         this.indexEngine.addIndex(index, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

   }

   public void addIndex(Index index, QueryOptions queryOptions) {
      queryOptions = this.openRequestScopeResourcesIfNecessary(queryOptions);

      try {
         this.indexEngine.addIndex(index, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

   }

   public Iterable getIndexes() {
      return this.indexEngine.getIndexes();
   }

   public int size() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      int var2;
      try {
         var2 = this.objectStore.size(queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var2;
   }

   public boolean isEmpty() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var2;
      try {
         var2 = this.objectStore.isEmpty(queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var2;
   }

   public boolean contains(Object o) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var3;
      try {
         var3 = this.objectStore.contains(o, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var3;
   }

   public Object[] toArray() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      Object[] var2;
      try {
         var2 = this.getObjectStoreAsSet(queryOptions).toArray();
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var2;
   }

   public Object[] toArray(Object[] a) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      Object[] var3;
      try {
         var3 = this.getObjectStoreAsSet(queryOptions).toArray(a);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var3;
   }

   public boolean containsAll(Collection c) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var3;
      try {
         var3 = this.objectStore.containsAll(c, queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var3;
   }

   public CloseableIterator iterator() {
      return new CloseableIterator() {
         final QueryOptions queryOptions = ConcurrentIndexedCollection.this.openRequestScopeResourcesIfNecessary((QueryOptions)null);
         private final CloseableIterator collectionIterator;
         boolean autoClosed;
         private Object currentObject;

         {
            this.collectionIterator = ConcurrentIndexedCollection.this.objectStore.iterator(this.queryOptions);
            this.autoClosed = false;
            this.currentObject = null;
         }

         public boolean hasNext() {
            boolean hasNext = this.collectionIterator.hasNext();
            if (!hasNext) {
               this.close();
               this.autoClosed = true;
            }

            return hasNext;
         }

         public Object next() {
            Object next = this.collectionIterator.next();
            this.currentObject = next;
            return next;
         }

         public void remove() {
            if (this.currentObject == null) {
               throw new IllegalStateException();
            } else {
               if (this.autoClosed) {
                  ConcurrentIndexedCollection.this.remove(this.currentObject);
               } else {
                  ConcurrentIndexedCollection.this.doRemoveAll(Collections.singleton(this.currentObject), this.queryOptions);
               }

               this.currentObject = null;
            }
         }

         public void close() {
            CloseableRequestResources.closeQuietly(this.collectionIterator);
            ConcurrentIndexedCollection.this.closeRequestScopeResourcesIfNecessary(this.queryOptions);
         }
      };
   }

   public boolean add(Object o) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var4;
      try {
         boolean modified = this.objectStore.add(o, queryOptions);
         this.indexEngine.addAll(ObjectSet.fromCollection(Collections.singleton(o)), queryOptions);
         var4 = modified;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var4;
   }

   public boolean remove(Object object) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var5;
      try {
         boolean modified = this.objectStore.remove(object, queryOptions);
         this.indexEngine.removeAll(ObjectSet.fromCollection(Collections.singleton(object)), queryOptions);
         var5 = modified;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var5;
   }

   public boolean addAll(Collection c) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var5;
      try {
         boolean modified = this.objectStore.addAll(c, queryOptions);
         this.indexEngine.addAll(ObjectSet.fromCollection(c), queryOptions);
         var5 = modified;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var5;
   }

   public boolean removeAll(Collection c) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var5;
      try {
         boolean modified = this.objectStore.removeAll(c, queryOptions);
         this.indexEngine.removeAll(ObjectSet.fromCollection(c), queryOptions);
         var5 = modified;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var5;
   }

   public boolean retainAll(Collection c) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);
      CloseableIterator iterator = null;

      try {
         boolean modified = false;
         iterator = this.objectStore.iterator(queryOptions);

         while(iterator.hasNext()) {
            Object next = iterator.next();
            if (!c.contains(next)) {
               this.doRemoveAll(Collections.singleton(next), queryOptions);
               modified = true;
            }
         }

         boolean var9 = modified;
         return var9;
      } finally {
         CloseableRequestResources.closeQuietly(iterator);
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }
   }

   public void clear() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      try {
         this.objectStore.clear(queryOptions);
         this.indexEngine.clear(queryOptions);
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

   }

   boolean doAddAll(Iterable objects, QueryOptions queryOptions) {
      if (objects instanceof Collection) {
         Collection c = (Collection)objects;
         boolean modified = this.objectStore.addAll(c, queryOptions);
         this.indexEngine.addAll(ObjectSet.fromCollection(c), queryOptions);
         return modified;
      } else {
         boolean modified = false;

         boolean added;
         for(Iterator var4 = objects.iterator(); var4.hasNext(); modified = added || modified) {
            Object object = var4.next();
            added = this.objectStore.add(object, queryOptions);
            this.indexEngine.addAll(ObjectSet.fromCollection(Collections.singleton(object)), queryOptions);
         }

         return modified;
      }
   }

   boolean doRemoveAll(Iterable objects, QueryOptions queryOptions) {
      if (objects instanceof Collection) {
         Collection c = (Collection)objects;
         boolean modified = this.objectStore.removeAll(c, queryOptions);
         this.indexEngine.removeAll(ObjectSet.fromCollection(c), queryOptions);
         return modified;
      } else {
         boolean modified = false;

         boolean removed;
         for(Iterator var4 = objects.iterator(); var4.hasNext(); modified = removed || modified) {
            Object object = var4.next();
            removed = this.objectStore.remove(object, queryOptions);
            this.indexEngine.removeAll(ObjectSet.fromCollection(Collections.singleton(object)), queryOptions);
         }

         return modified;
      }
   }

   protected QueryOptions openRequestScopeResourcesIfNecessary(QueryOptions queryOptions) {
      if (queryOptions == null) {
         queryOptions = new QueryOptions();
      }

      if (!(this.persistence instanceof OnHeapPersistence)) {
         this.persistence.openRequestScopeResources(queryOptions);
      }

      queryOptions.put(Persistence.class, this.persistence);
      return queryOptions;
   }

   protected void closeRequestScopeResourcesIfNecessary(QueryOptions queryOptions) {
      if (!(this.persistence instanceof OnHeapPersistence)) {
         this.persistence.closeRequestScopeResources(queryOptions);
      }

   }

   protected ObjectStoreAsSet getObjectStoreAsSet(QueryOptions queryOptions) {
      return new ObjectStoreAsSet(this.objectStore, queryOptions);
   }

   public boolean equals(Object o) {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      boolean var4;
      try {
         boolean var8;
         if (this == o) {
            var8 = true;
            return var8;
         }

         if (!(o instanceof Set)) {
            var8 = false;
            return var8;
         }

         Set that = (Set)o;
         if (!this.getObjectStoreAsSet(queryOptions).equals(that)) {
            var4 = false;
            return var4;
         }

         var4 = true;
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var4;
   }

   public int hashCode() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      int var2;
      try {
         var2 = this.getObjectStoreAsSet(queryOptions).hashCode();
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var2;
   }

   public String toString() {
      QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

      String var2;
      try {
         var2 = this.getObjectStoreAsSet(queryOptions).toString();
      } finally {
         this.closeRequestScopeResourcesIfNecessary(queryOptions);
      }

      return var2;
   }

   protected static void flagAsReadRequest(QueryOptions queryOptions) {
      FlagsEnabled.forQueryOptions(queryOptions).add(PersistenceFlags.READ_REQUEST);
   }
}
