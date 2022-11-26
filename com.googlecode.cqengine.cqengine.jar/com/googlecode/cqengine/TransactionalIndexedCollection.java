package com.googlecode.cqengine;

import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.onheap.OnHeapPersistence;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.ArgumentValidationOption;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.IsolationLevel;
import com.googlecode.cqengine.query.option.IsolationOption;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.closeable.CloseableFilteringResultSet;
import com.googlecode.cqengine.resultset.closeable.CloseableResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionalIndexedCollection extends ConcurrentIndexedCollection {
   public static final String STRICT_REPLACEMENT = "STRICT_REPLACEMENT";
   final Class objectType;
   volatile Version currentVersion;
   final Object writeMutex;
   final AtomicLong versionNumberGenerator;

   public TransactionalIndexedCollection(Class objectType) {
      this(objectType, OnHeapPersistence.withoutPrimaryKey());
   }

   public TransactionalIndexedCollection(Class objectType, Persistence persistence) {
      super(persistence);
      this.writeMutex = new Object();
      this.versionNumberGenerator = new AtomicLong();
      this.objectType = objectType;
      this.currentVersion = new Version(Collections.emptySet());
   }

   void incrementVersion(Iterable objectsToExcludeFromNextVersion) {
      Version previousVersion = this.currentVersion;
      this.currentVersion = new Version(objectsToExcludeFromNextVersion);
      previousVersion.lock.writeLock().lock();
   }

   Version acquireReadLockForCurrentVersion() {
      Version currentVersion;
      do {
         currentVersion = this.currentVersion;
      } while(!currentVersion.lock.readLock().tryLock());

      return currentVersion;
   }

   public boolean update(Iterable objectsToRemove, Iterable objectsToAdd) {
      return this.update(objectsToRemove, objectsToAdd, QueryFactory.noQueryOptions());
   }

   public boolean update(Iterable objectsToRemove, Iterable objectsToAdd, QueryOptions queryOptions) {
      if (IsolationOption.isIsolationLevel(queryOptions, IsolationLevel.READ_UNCOMMITTED)) {
         return super.update(objectsToRemove, objectsToAdd, queryOptions);
      } else {
         if (!ArgumentValidationOption.isSkip(queryOptions)) {
            ensureUpdateSetsAreDisjoint(objectsToRemove, objectsToAdd);
         }

         synchronized(this.writeMutex) {
            queryOptions = this.openRequestScopeResourcesIfNecessary(queryOptions);

            boolean var8;
            try {
               Iterator objectsToRemoveIterator = objectsToRemove.iterator();
               Iterator objectsToAddIterator = objectsToAdd.iterator();
               boolean modified;
               if (!objectsToRemoveIterator.hasNext() && !objectsToAddIterator.hasNext()) {
                  modified = false;
                  return modified;
               }

               if (FlagsEnabled.isFlagEnabled(queryOptions, "STRICT_REPLACEMENT") && !objectStoreContainsAllIterable(this.objectStore, objectsToRemove, queryOptions)) {
                  modified = false;
                  return modified;
               }

               modified = false;
               if (objectsToAddIterator.hasNext()) {
                  this.incrementVersion(objectsToAdd);
                  modified = this.doAddAll(objectsToAdd, queryOptions);
               }

               if (objectsToRemoveIterator.hasNext()) {
                  this.incrementVersion(objectsToRemove);
                  modified = this.doRemoveAll(objectsToRemove, queryOptions) || modified;
               }

               this.incrementVersion(Collections.emptySet());
               var8 = modified;
            } finally {
               this.closeRequestScopeResourcesIfNecessary(queryOptions);
            }

            return var8;
         }
      }
   }

   public boolean add(Object o) {
      return this.update(Collections.emptySet(), Collections.singleton(o));
   }

   public boolean remove(Object object) {
      return this.update(Collections.singleton(object), Collections.emptySet());
   }

   public boolean addAll(Collection c) {
      return this.update(Collections.emptySet(), c);
   }

   public boolean removeAll(Collection c) {
      return this.update(c, Collections.emptySet());
   }

   public boolean retainAll(Collection c) {
      synchronized(this.writeMutex) {
         QueryOptions queryOptions = this.openRequestScopeResourcesIfNecessary((QueryOptions)null);

         boolean var8;
         try {
            Set objectsToRetain = new HashSet(c.size());
            Iterator var5 = c.iterator();

            while(var5.hasNext()) {
               Object object = var5.next();
               if (object != null) {
                  objectsToRetain.add(object);
               }
            }

            ResultSet objectsToRemove = super.retrieve(QueryFactory.not(QueryFactory.in(QueryFactory.selfAttribute(this.objectType), (Collection)objectsToRetain)), queryOptions);
            Iterator objectsToRemoveIterator = objectsToRemove.iterator();
            boolean modified;
            if (!objectsToRemoveIterator.hasNext()) {
               modified = false;
               return modified;
            }

            this.incrementVersion(objectsToRemove);
            modified = this.doRemoveAll(objectsToRemove, queryOptions);
            this.incrementVersion(Collections.emptySet());
            var8 = modified;
         } finally {
            this.closeRequestScopeResourcesIfNecessary(queryOptions);
         }

         return var8;
      }
   }

   public synchronized void clear() {
      this.retainAll(Collections.emptySet());
   }

   public ResultSet retrieve(Query query) {
      return this.retrieve(query, QueryFactory.noQueryOptions());
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      if (IsolationOption.isIsolationLevel(queryOptions, IsolationLevel.READ_UNCOMMITTED)) {
         return super.retrieve(query, queryOptions);
      } else {
         final Version thisVersion = this.acquireReadLockForCurrentVersion();
         ResultSet results = super.retrieve(query, queryOptions);
         CloseableResultSet versionReadingResultSet = new CloseableResultSet(results, query, queryOptions) {
            public void close() {
               super.close();
               thisVersion.lock.readLock().unlock();
            }
         };
         return (ResultSet)(thisVersion.objectsToExclude.iterator().hasNext() ? new CloseableFilteringResultSet(versionReadingResultSet, query, queryOptions) {
            public boolean isValid(Object object, QueryOptions queryOptions) {
               Iterable objectsToExclude = thisVersion.objectsToExclude;
               return !TransactionalIndexedCollection.iterableContains(objectsToExclude, object);
            }
         } : versionReadingResultSet);
      }
   }

   static boolean iterableContains(Iterable objects, Object o) {
      if (objects instanceof Collection) {
         return ((Collection)objects).contains(o);
      } else {
         return objects instanceof ResultSet ? ((ResultSet)objects).contains(o) : IteratorUtil.iterableContains(objects, o);
      }
   }

   static boolean objectStoreContainsAllIterable(ObjectStore objectStore, Iterable candidates, QueryOptions queryOptions) {
      if (candidates instanceof Collection) {
         return objectStore.containsAll((Collection)candidates, queryOptions);
      } else {
         Iterator var3 = candidates.iterator();

         Object candidate;
         do {
            if (!var3.hasNext()) {
               return true;
            }

            candidate = var3.next();
         } while(objectStore.contains(candidate, queryOptions));

         return false;
      }
   }

   static void ensureUpdateSetsAreDisjoint(Iterable objectsToRemove, Iterable objectsToAdd) {
      Iterator var2 = objectsToRemove.iterator();

      Object objectToRemove;
      do {
         if (!var2.hasNext()) {
            return;
         }

         objectToRemove = var2.next();
      } while(!iterableContains(objectsToAdd, objectToRemove));

      throw new IllegalArgumentException("The sets of objectsToRemove and objectsToAdd are not disjoint [for all objectsToRemove, objectToRemove.equals(objectToAdd) must return false].");
   }

   class Version {
      final ReadWriteLock lock = new ReentrantReadWriteLock();
      final Iterable objectsToExclude;
      final long versionNumber;

      Version(Iterable objectsToExclude) {
         this.versionNumber = TransactionalIndexedCollection.this.versionNumberGenerator.incrementAndGet();
         this.objectsToExclude = objectsToExclude;
      }
   }
}
