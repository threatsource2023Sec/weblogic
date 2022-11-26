package weblogic.utils.classloaders.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClassFinderIndex;

public class EagerClassFinderIndex extends ClassFinderIndex {
   private volatile List postFreezeAdditions = new ArrayList();

   public EagerClassFinderIndex(List finders) {
      super(finders);
   }

   public EagerClassFinderIndex(List finders, Map index) {
      super(finders, index);
   }

   public void update(ClassFinder finder, int codeSourceIndex) {
      this.index(finder, codeSourceIndex);
   }

   public Iterator iterator(String packageName) {
      List pFA = this.postFreezeAdditions;
      return (Iterator)(pFA.isEmpty() ? new PackageIndexedIterator(packageName) : new PackageIndexedPlusPostFreezeAddition(packageName, pFA));
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
         } else {
            return this.nextPostFreeze != null || this.iPostFreeze.hasNext();
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
            if (this.nextPostFreeze == null && this.iPostFreeze.hasNext()) {
               this.nextPostFreeze = (PostFreezeAddition)this.iPostFreeze.next();
            }

            if (this.nextPostFreeze != null) {
               this.currentIndex = this.nextPostFreeze.effectiveIndex;
               ClassFinder finder = this.nextPostFreeze.finder;
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
      protected final List list = EagerClassFinderIndex.this.getList();
      protected int currentIndex = -1;
      protected int iterationIndex;
      protected final int iterationSize;
      protected final int[] its;

      public PackageIndexedIterator(String packageName) {
         this.iteration = PackageIndices.merge((PackageIndices)EagerClassFinderIndex.this.getMap().get(packageName), EagerClassFinderIndex.this.getUnindexable());
         this.iterationSize = this.iteration.size();
         this.its = this.iteration.rawAccess();
         this.iterationIndex = 0;
      }

      public boolean hasNext() {
         return this.iteration != null && this.iterationIndex < this.iterationSize;
      }

      public ClassFinder next() {
         if (this.iterationIndex < this.iterationSize) {
            this.currentIndex = this.its[this.iterationIndex++];
            return (ClassFinder)this.list.get(this.currentIndex);
         } else {
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
}
