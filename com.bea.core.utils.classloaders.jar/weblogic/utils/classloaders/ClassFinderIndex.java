package weblogic.utils.classloaders;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.classloaders.index.PackageIndices;

public abstract class ClassFinderIndex implements ClassFinderWalker {
   private final List finders;
   private final Map map;
   private int[] unindexable;

   protected ClassFinderIndex(List finders) {
      this(finders, new HashMap());
   }

   protected ClassFinderIndex(List finders, Map map) {
      this.unindexable = null;
      this.finders = finders;
      this.map = map;
   }

   public abstract void update(ClassFinder var1, int var2);

   public void build(Runnable onFinishOptimization) {
      int size = this.finders.size();

      for(int i = 0; i < size; ++i) {
         ClassFinder finder = (ClassFinder)this.finders.get(i);
         this.update(finder, i);
      }

      if (onFinishOptimization != null) {
         onFinishOptimization.run();
      }

   }

   public Iterator iterator(String packageName) {
      return new Iterator() {
         List list = ClassFinderIndex.this.getList();
         private final int count;
         private int currentIndex;

         {
            this.count = this.list.size();
            this.currentIndex = -1;
         }

         public boolean hasNext() {
            return this.currentIndex + 1 < this.count;
         }

         public ClassFinder next() {
            ++this.currentIndex;
            return this.currentIndex < this.count ? (ClassFinder)this.list.get(this.currentIndex) : null;
         }

         public void remove() {
            throw new UnsupportedOperationException("remove");
         }
      };
   }

   public Map getMap() {
      return this.map;
   }

   public int[] getUnindexable() {
      return this.unindexable;
   }

   protected void index(ClassFinder finder, int codeSourceIndex) {
      Map index = this.getMap();
      if (finder instanceof PackageIndexedClassFinder) {
         Collection packs = ((PackageIndexedClassFinder)finder).getPackageNames();
         PackageIndices indices;
         if (packs != null) {
            for(Iterator var5 = packs.iterator(); var5.hasNext(); indices.append(codeSourceIndex)) {
               String packageName = (String)var5.next();
               indices = (PackageIndices)index.get(packageName);
               if (indices == null) {
                  PackageIndices created = this.createPackageIndices();
                  indices = this.putIfAbsent(index, packageName, created);
                  if (indices == null) {
                     indices = created;
                  }
               }
            }
         } else {
            this.addUnindexable(codeSourceIndex);
         }
      } else {
         this.addUnindexable(codeSourceIndex);
      }

   }

   protected PackageIndices createPackageIndices() {
      return new PackageIndices();
   }

   protected PackageIndices putIfAbsent(Map index, String packageName, PackageIndices indices) {
      return (PackageIndices)index.put(packageName, indices);
   }

   protected void addUnindexable(int codeSourceIndex) {
      if (this.unindexable == null) {
         this.unindexable = new int[1];
         this.unindexable[0] = codeSourceIndex;
      } else {
         int[] copy = new int[this.unindexable.length + 1];
         System.arraycopy(this.unindexable, 0, copy, 0, this.unindexable.length);
         copy[this.unindexable.length] = codeSourceIndex;
         this.unindexable = copy;
      }

   }

   public boolean canPersist() {
      return false;
   }

   protected List getList() {
      return this.finders;
   }
}
