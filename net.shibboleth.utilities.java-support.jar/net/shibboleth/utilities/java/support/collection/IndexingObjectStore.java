package net.shibboleth.utilities.java.support.collection;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class IndexingObjectStore {
   private ReadWriteLock rwLock = new ReentrantReadWriteLock();
   private Map objectStore = new LazyMap();
   private Map indexStore = new LazyMap();
   private int lastIndex = 0;

   public void clear() {
      Lock writeLock = this.rwLock.writeLock();
      writeLock.lock();

      try {
         this.objectStore.clear();
         this.indexStore.clear();
      } finally {
         writeLock.unlock();
      }

   }

   public boolean containsIndex(String index) {
      Lock readLock = this.rwLock.readLock();
      readLock.lock();

      boolean var3;
      try {
         var3 = this.objectStore.containsKey(index);
      } finally {
         readLock.unlock();
      }

      return var3;
   }

   public boolean containsInstance(Object instance) {
      Lock readLock = this.rwLock.readLock();
      readLock.lock();

      boolean var4;
      try {
         Integer index = (Integer)this.indexStore.get(instance);
         if (index != null) {
            var4 = this.objectStore.containsKey(index.toString());
            return var4;
         }

         var4 = false;
      } finally {
         readLock.unlock();
      }

      return var4;
   }

   public boolean isEmpty() {
      Lock readLock = this.rwLock.readLock();
      readLock.lock();

      boolean var2;
      try {
         var2 = this.objectStore.isEmpty();
      } finally {
         readLock.unlock();
      }

      return var2;
   }

   public String put(Object object) {
      if (object == null) {
         return null;
      } else {
         Lock writeLock = this.rwLock.writeLock();
         writeLock.lock();

         String var5;
         try {
            String index = this.getIndex(object);
            StoredObjectWrapper objectWrapper = (StoredObjectWrapper)this.objectStore.get(index);
            if (objectWrapper == null) {
               objectWrapper = new StoredObjectWrapper(object);
               this.objectStore.put(index, objectWrapper);
            }

            objectWrapper.incremementReferenceCount();
            var5 = index;
         } finally {
            writeLock.unlock();
         }

         return var5;
      }
   }

   public Object get(String index) {
      if (index == null) {
         return null;
      } else {
         Lock readLock = this.rwLock.readLock();
         readLock.lock();

         Object var4;
         try {
            StoredObjectWrapper objectWrapper = (StoredObjectWrapper)this.objectStore.get(index);
            if (objectWrapper != null) {
               var4 = objectWrapper.getObject();
               return var4;
            }

            var4 = null;
         } finally {
            readLock.unlock();
         }

         return var4;
      }
   }

   public void remove(String index) {
      if (index != null) {
         Lock writeLock = this.rwLock.writeLock();
         writeLock.lock();

         try {
            StoredObjectWrapper objectWrapper = (StoredObjectWrapper)this.objectStore.get(index);
            if (objectWrapper != null) {
               objectWrapper.decremementReferenceCount();
               if (objectWrapper.getReferenceCount() == 0) {
                  this.objectStore.remove(index);
                  this.removeIndex(objectWrapper.getObject());
               }
            }
         } finally {
            writeLock.unlock();
         }

      }
   }

   public int size() {
      Lock readLock = this.rwLock.readLock();
      readLock.lock();

      int var2;
      try {
         var2 = this.objectStore.size();
      } finally {
         readLock.unlock();
      }

      return var2;
   }

   protected String getIndex(Object object) {
      Integer index = (Integer)this.indexStore.get(object);
      if (index == null) {
         index = ++this.lastIndex;
         this.indexStore.put(object, index);
      }

      return index.toString();
   }

   protected void removeIndex(Object object) {
      this.indexStore.remove(object);
   }

   private class StoredObjectWrapper {
      private Object object;
      private int referenceCount;

      public StoredObjectWrapper(Object wrappedObject) {
         this.object = wrappedObject;
         this.referenceCount = 0;
      }

      public Object getObject() {
         return this.object;
      }

      public int getReferenceCount() {
         return this.referenceCount;
      }

      public void incremementReferenceCount() {
         ++this.referenceCount;
      }

      public void decremementReferenceCount() {
         --this.referenceCount;
      }
   }
}
