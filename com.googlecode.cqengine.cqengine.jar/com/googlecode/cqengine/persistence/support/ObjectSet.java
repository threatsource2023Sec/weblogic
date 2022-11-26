package com.googlecode.cqengine.persistence.support;

import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.io.Closeable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ObjectSet implements CloseableIterable, Closeable {
   public abstract void close();

   public abstract boolean isEmpty();

   public static ObjectSet fromObjectStore(ObjectStore objectStore, QueryOptions queryOptions) {
      return new ObjectStoreAsObjectSet(objectStore, queryOptions);
   }

   public static ObjectSet fromCollection(Collection collection) {
      return new CollectionAsObjectSet(collection);
   }

   static class ObjectStoreAsObjectSet extends ObjectSet {
      final ObjectStore objectStore;
      final QueryOptions queryOptions;
      final Set openIterators = new HashSet();

      public ObjectStoreAsObjectSet(ObjectStore objectStore, QueryOptions queryOptions) {
         this.objectStore = objectStore;
         this.queryOptions = queryOptions;
      }

      public CloseableIterator iterator() {
         final CloseableIterator iterator = this.objectStore.iterator(this.queryOptions);
         this.openIterators.add(iterator);
         return new CloseableIterator() {
            public void close() {
               ObjectStoreAsObjectSet.this.openIterators.remove(this);
               iterator.close();
            }

            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Object next() {
               return iterator.next();
            }

            public void remove() {
               iterator.remove();
            }
         };
      }

      public boolean isEmpty() {
         CloseableIterator iterator = this.objectStore.iterator(this.queryOptions);

         boolean var2;
         try {
            var2 = !iterator.hasNext();
         } finally {
            iterator.close();
         }

         return var2;
      }

      public void close() {
         Iterator var1 = this.openIterators.iterator();

         while(var1.hasNext()) {
            CloseableIterator openIterator = (CloseableIterator)var1.next();
            openIterator.close();
         }

         this.openIterators.clear();
      }
   }

   static class CollectionAsObjectSet extends ObjectSet {
      final Collection collection;

      public CollectionAsObjectSet(Collection collection) {
         this.collection = collection;
      }

      public CloseableIterator iterator() {
         final Iterator iterator = this.collection.iterator();
         return new CloseableIterator() {
            public void close() {
            }

            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Object next() {
               return iterator.next();
            }

            public void remove() {
               iterator.remove();
            }
         };
      }

      public boolean isEmpty() {
         return this.collection.isEmpty();
      }

      public void close() {
      }
   }
}
