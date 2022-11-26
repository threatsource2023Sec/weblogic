package weblogic.utils.classloaders.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClassFinderIndex;
import weblogic.work.ExecuteThreadLite;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class BackgroundClassFinderIndex extends ClassFinderIndex {
   private final AtomicInteger idxIteration = new AtomicInteger(0);
   private final ReadWriteLock packageIndicesLock = new ReentrantReadWriteLock();
   private volatile List postFreezeAdditions = new ArrayList();

   public BackgroundClassFinderIndex(List finders) {
      super(finders, new ConcurrentHashMap());
   }

   public void update(ClassFinder finder, int codeSourceIndex) {
      this.index(finder, codeSourceIndex);
   }

   public void build(Runnable onFinishOptimization) {
      this.doIndexing(onFinishOptimization);
   }

   int getIndexingIteration() {
      return this.idxIteration.get();
   }

   private void buildInternal() {
      List finders = this.getList();
      int size = finders.size();

      int i;
      while((i = this.idxIteration.getAndIncrement()) < size) {
         ClassFinder f = (ClassFinder)finders.get(i);
         this.update(f, i);
      }

   }

   protected Runnable createIndexingRunnable(final Runnable onFinishOptimization) {
      return new Runnable() {
         public void run() {
            BackgroundClassFinderIndex.this.buildInternal();
            if (onFinishOptimization != null) {
               onFinishOptimization.run();
            }

         }
      };
   }

   protected WorkManager selectWorkManager() {
      WorkManager wm = null;
      Thread current = Thread.currentThread();
      if (current instanceof ExecuteThreadLite) {
         wm = ((ExecuteThreadLite)current).getWorkManager();
      }

      if (wm == null) {
         wm = WorkManagerFactory.getInstance().getSystem();
      }

      return (WorkManager)wm;
   }

   private void doIndexing(Runnable onFinishOptimization) {
      Runnable run = this.createIndexingRunnable(onFinishOptimization);
      WorkManager wm = this.selectWorkManager();
      wm.schedule(run);
   }

   protected void addUnindexable(int codeSourceIndex) {
      Lock lock = this.packageIndicesLock.writeLock();
      lock.lock();

      try {
         super.addUnindexable(codeSourceIndex);
      } finally {
         lock.unlock();
      }

   }

   public int[] getUnindexable() {
      Lock lock = this.packageIndicesLock.readLock();
      lock.lock();

      int[] var2;
      try {
         var2 = super.getUnindexable();
      } finally {
         lock.unlock();
      }

      return var2;
   }

   protected PackageIndices createPackageIndices() {
      return new ConcurrentPackageIndices();
   }

   protected PackageIndices putIfAbsent(Map index, String packageName, PackageIndices indices) {
      return (PackageIndices)((ConcurrentMap)index).putIfAbsent(packageName, indices);
   }

   public Iterator iterator(String packageName) {
      List pFA = this.postFreezeAdditions;
      return (Iterator)(pFA.isEmpty() ? new PackageIndexedIterator(packageName) : new PackageIndexedPlusPostFreezeAddition(packageName, pFA));
   }

   public ConcurrentMap getMap() {
      return (ConcurrentMap)super.getMap();
   }

   public boolean canPersist() {
      return true;
   }

   public synchronized void handlePostFreezeAddition(int effectiveIndex, ClassFinder finder) {
      List changed = new ArrayList(this.postFreezeAdditions);
      ListIterator it = changed.listIterator(changed.size());
      boolean isAdded = false;

      while(it.hasPrevious()) {
         PostFreezeAddition prev = (PostFreezeAddition)it.previous();
         if (effectiveIndex >= prev.effectiveIndex) {
            it.add(new PostFreezeAddition(effectiveIndex, finder));
            isAdded = true;
            break;
         }
      }

      if (!isAdded) {
         changed.add(0, new PostFreezeAddition(effectiveIndex, finder));
      }

      this.postFreezeAdditions = changed;
   }

   private static final class PostFreezeAddition {
      final int effectiveIndex;
      final ClassFinder finder;

      PostFreezeAddition(int effectiveIndex, ClassFinder finder) {
         this.effectiveIndex = effectiveIndex;
         this.finder = finder;
      }
   }

   protected class PackageIndexedPlusPostFreezeAddition extends PackageIndexedIterator {
      private final Iterator iPostFreeze;
      private PostFreezeAddition nextPostFreeze;

      public PackageIndexedPlusPostFreezeAddition(String packageName, List pFA) {
         super(packageName);
         this.iPostFreeze = pFA.iterator();
         this.nextPostFreeze = (PostFreezeAddition)this.iPostFreeze.next();
      }

      public boolean hasNext() {
         if (this.iterationIndex < this.iterationSize) {
            return true;
         } else if (this.nextPostFreeze == null && !this.iPostFreeze.hasNext()) {
            if (this.currentIndex >= this.indexingIndex && this.currentIndex + 1 == this.list.size()) {
               return false;
            } else {
               return this.indexingIndex < this.list.size();
            }
         } else {
            return true;
         }
      }

      public ClassFinder next() {
         if (this.iterationIndex < this.iterationSize) {
            int tmp = this.its[this.iterationIndex++];
            if (this.nextPostFreeze == null && this.iPostFreeze.hasNext()) {
               this.nextPostFreeze = (PostFreezeAddition)this.iPostFreeze.next();
            }

            if (this.nextPostFreeze != null && tmp >= this.nextPostFreeze.effectiveIndex) {
               this.currentIndex = this.nextPostFreeze.effectiveIndex;
               --this.iterationIndex;
               ClassFinder finderx = this.nextPostFreeze.finder;
               this.nextPostFreeze = null;
               return finderx;
            } else {
               this.currentIndex = tmp;
               return (ClassFinder)this.list.get(this.currentIndex);
            }
         } else {
            ClassFinder finder;
            if (this.indexingIndex < this.list.size()) {
               this.currentIndex = this.currentIndex < this.indexingIndex ? this.indexingIndex : this.currentIndex + 1;
               if (this.currentIndex < this.list.size()) {
                  if (this.nextPostFreeze == null && this.iPostFreeze.hasNext()) {
                     this.nextPostFreeze = (PostFreezeAddition)this.iPostFreeze.next();
                  }

                  if (this.nextPostFreeze != null && this.currentIndex >= this.nextPostFreeze.effectiveIndex) {
                     this.currentIndex = this.nextPostFreeze.effectiveIndex;
                     finder = this.nextPostFreeze.finder;
                     this.nextPostFreeze = null;
                     return finder;
                  }

                  return (ClassFinder)this.list.get(this.currentIndex);
               }
            }

            if (this.nextPostFreeze == null && this.iPostFreeze.hasNext()) {
               this.nextPostFreeze = (PostFreezeAddition)this.iPostFreeze.next();
            }

            if (this.nextPostFreeze != null) {
               this.currentIndex = this.nextPostFreeze.effectiveIndex;
               finder = this.nextPostFreeze.finder;
               this.nextPostFreeze = null;
               return finder;
            } else {
               return null;
            }
         }
      }
   }

   protected class PackageIndexedIterator implements Iterator {
      private final PackageIndices iteration;
      protected final List list = BackgroundClassFinderIndex.this.getList();
      protected final int indexingIndex;
      protected int currentIndex;
      protected final int iterationSize;
      protected final int[] its;
      protected int iterationIndex;

      public PackageIndexedIterator(String packageName) {
         this.indexingIndex = BackgroundClassFinderIndex.this.idxIteration.get() - 1;
         this.currentIndex = -1;
         ConcurrentMap map = BackgroundClassFinderIndex.this.getMap();
         PackageIndices it = (PackageIndices)map.get(packageName);
         if (it == null) {
            PackageIndices created = BackgroundClassFinderIndex.this.createPackageIndices();
            it = (PackageIndices)map.putIfAbsent(packageName, created);
            if (it == null) {
               it = created;
            }
         }

         it = PackageIndices.merge(it, BackgroundClassFinderIndex.this.getUnindexable());
         this.iterationSize = it.size();
         this.its = it.rawAccess();
         this.iteration = it;
         this.iterationIndex = 0;
      }

      public boolean hasNext() {
         if (this.iterationIndex < this.iterationSize) {
            return true;
         } else if (this.currentIndex >= this.indexingIndex && this.currentIndex + 1 == this.list.size()) {
            return false;
         } else {
            return this.indexingIndex < this.list.size();
         }
      }

      public ClassFinder next() {
         if (this.iterationIndex < this.iterationSize) {
            this.currentIndex = this.its[this.iterationIndex++];
            return (ClassFinder)this.list.get(this.currentIndex);
         } else {
            if (this.indexingIndex < this.list.size()) {
               this.currentIndex = this.currentIndex < this.indexingIndex ? this.indexingIndex : this.currentIndex + 1;
               if (this.currentIndex < this.list.size()) {
                  return (ClassFinder)this.list.get(this.currentIndex);
               }
            }

            return null;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("remove");
      }

      public int getCurrentIndex() {
         return this.currentIndex;
      }

      public boolean shouldDefineCurrentPackage() {
         if (this.iteration.getPackageDefinedIndex() < 0) {
            this.iteration.setPackageDefinedIndex(this.currentIndex);
            return true;
         } else {
            return false;
         }
      }
   }

   protected class ConcurrentPackageIndices extends PackageIndices {
      public ConcurrentPackageIndices() {
      }

      public ConcurrentPackageIndices(int firstIndex) {
         super(firstIndex);
      }

      public boolean insert(int newIndex) {
         Lock lock = BackgroundClassFinderIndex.this.packageIndicesLock.writeLock();
         lock.lock();

         boolean var3;
         try {
            var3 = super.insert(newIndex);
         } finally {
            lock.unlock();
         }

         return var3;
      }

      public void append(int newIndex) {
         Lock lock = BackgroundClassFinderIndex.this.packageIndicesLock.writeLock();
         lock.lock();

         try {
            super.append(newIndex);
         } finally {
            lock.unlock();
         }

      }

      public int size() {
         Lock lock = BackgroundClassFinderIndex.this.packageIndicesLock.readLock();
         lock.lock();

         int var2;
         try {
            var2 = super.size();
         } finally {
            lock.unlock();
         }

         return var2;
      }

      int[] rawAccess() {
         Lock lock = BackgroundClassFinderIndex.this.packageIndicesLock.readLock();
         lock.lock();

         int[] var2;
         try {
            var2 = super.rawAccess();
         } finally {
            lock.unlock();
         }

         return var2;
      }

      public int get(int offset) {
         Lock lock = BackgroundClassFinderIndex.this.packageIndicesLock.readLock();
         lock.lock();

         int var3;
         try {
            var3 = super.get(offset);
         } finally {
            lock.unlock();
         }

         return var3;
      }

      public synchronized int getPackageDefinedIndex() {
         return super.getPackageDefinedIndex();
      }

      public synchronized void setPackageDefinedIndex(int packageDefinedIndex) {
         super.setPackageDefinedIndex(packageDefinedIndex);
      }
   }
}
