package com.googlecode.cqengine;

import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.CloseableRequestResources;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.onheap.OnHeapPersistence;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectLockingIndexedCollection extends ConcurrentIndexedCollection {
   final StripedLock stripedLock;

   public ObjectLockingIndexedCollection() {
      this(OnHeapPersistence.withoutPrimaryKey(), 64);
   }

   public ObjectLockingIndexedCollection(Persistence persistence) {
      this(persistence, 64);
   }

   public ObjectLockingIndexedCollection(int concurrencyLevel) {
      this(OnHeapPersistence.withoutPrimaryKey(), concurrencyLevel);
   }

   public ObjectLockingIndexedCollection(Persistence persistence, int concurrencyLevel) {
      super(persistence);
      this.stripedLock = new StripedLock(concurrencyLevel);
   }

   public CloseableIterator iterator() {
      return new CloseableIterator() {
         final QueryOptions queryOptions = ObjectLockingIndexedCollection.this.openRequestScopeResourcesIfNecessary((QueryOptions)null);
         private final CloseableIterator collectionIterator;
         boolean autoClosed;
         private Object currentObject;

         {
            this.collectionIterator = ObjectLockingIndexedCollection.this.objectStore.iterator(this.queryOptions);
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
               Lock lock = ObjectLockingIndexedCollection.this.stripedLock.getLockForObject(this.currentObject);
               lock.lock();

               try {
                  if (this.autoClosed) {
                     ObjectLockingIndexedCollection.this.remove(this.currentObject);
                  } else {
                     ObjectLockingIndexedCollection.this.doRemoveAll(Collections.singleton(this.currentObject), this.queryOptions);
                  }

                  this.currentObject = null;
               } finally {
                  lock.unlock();
               }

            }
         }

         public void close() {
            CloseableRequestResources.closeQuietly(this.collectionIterator);
            ObjectLockingIndexedCollection.this.closeRequestScopeResourcesIfNecessary(this.queryOptions);
         }
      };
   }

   public boolean add(Object o) {
      Lock lock = this.stripedLock.getLockForObject(o);
      lock.lock();

      boolean var3;
      try {
         var3 = super.add(o);
      } finally {
         lock.unlock();
      }

      return var3;
   }

   public boolean remove(Object object) {
      Lock lock = this.stripedLock.getLockForObject(object);
      lock.lock();

      boolean var3;
      try {
         var3 = super.remove(object);
      } finally {
         lock.unlock();
      }

      return var3;
   }

   public boolean addAll(Collection c) {
      boolean modified = false;

      Object object;
      for(Iterator var3 = c.iterator(); var3.hasNext(); modified = this.add(object) || modified) {
         object = var3.next();
      }

      return modified;
   }

   public boolean removeAll(Collection c) {
      boolean modified = false;

      Object object;
      for(Iterator var3 = c.iterator(); var3.hasNext(); modified = this.remove(object) || modified) {
         object = var3.next();
      }

      return modified;
   }

   public boolean retainAll(Collection c) {
      return super.retainAll(c);
   }

   public synchronized void clear() {
      super.clear();
   }

   static class StripedLock {
      final int concurrencyLevel;
      final ReentrantLock[] locks;

      StripedLock(int concurrencyLevel) {
         this.concurrencyLevel = concurrencyLevel;
         this.locks = new ReentrantLock[concurrencyLevel];

         for(int i = 0; i < concurrencyLevel; ++i) {
            this.locks[i] = new ReentrantLock();
         }

      }

      ReentrantLock getLockForObject(Object object) {
         int hashCode = object.hashCode();
         return this.locks[Math.abs(hashCode % this.concurrencyLevel)];
      }
   }
}
