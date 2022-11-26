package net.shibboleth.utilities.java.support.collection;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;

public class LockableClassToInstanceMultiMap extends ClassToInstanceMultiMap {
   @Nonnull
   private final ReadWriteLock readWriteLock;

   public LockableClassToInstanceMultiMap() {
      this(false);
   }

   public LockableClassToInstanceMultiMap(boolean isIndexingSupertypes) {
      super(isIndexingSupertypes);
      this.readWriteLock = new ReentrantReadWriteLock(true);
   }

   @Nonnull
   public ReadWriteLock getReadWriteLock() {
      return this.readWriteLock;
   }

   public void clearWithLock() {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.clear();
      } finally {
         writeLock.unlock();
      }

   }

   public boolean containsKeyWithLock(@Nullable Class key) {
      Lock readLock = this.getReadWriteLock().readLock();

      boolean var3;
      try {
         readLock.lock();
         var3 = this.containsKey(key);
      } finally {
         readLock.unlock();
      }

      return var3;
   }

   public boolean containsValueWithLock(@Nonnull Object value) {
      Lock readLock = this.getReadWriteLock().readLock();

      boolean var3;
      try {
         readLock.lock();
         var3 = this.containsValue(value);
      } finally {
         readLock.unlock();
      }

      return var3;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public List getWithLock(@Nullable Class type) {
      Lock readLock = this.getReadWriteLock().readLock();

      List var3;
      try {
         readLock.lock();
         var3 = this.get(type);
      } finally {
         readLock.unlock();
      }

      return var3;
   }

   public boolean isEmptyWithLock() {
      Lock readLock = this.getReadWriteLock().readLock();

      boolean var2;
      try {
         readLock.lock();
         var2 = this.isEmpty();
      } finally {
         readLock.unlock();
      }

      return var2;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public Set keysWithLock() {
      Lock readLock = this.getReadWriteLock().readLock();

      Set var2;
      try {
         readLock.lock();
         var2 = this.keys();
      } finally {
         readLock.unlock();
      }

      return var2;
   }

   public void putWithLock(@Nonnull Object value) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.put(value);
      } finally {
         writeLock.unlock();
      }

   }

   public void putAllWithLock(@Nullable @NonnullElements ClassToInstanceMultiMap map) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.putAll(map);
      } finally {
         writeLock.unlock();
      }

   }

   public void putAllWithLock(@Nullable @NonnullElements Iterable newValues) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.putAll(newValues);
      } finally {
         writeLock.unlock();
      }

   }

   public void removeWithLock(@Nonnull Object value) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.remove(value);
      } finally {
         writeLock.unlock();
      }

   }

   public void removeWithLock(@Nullable Class type) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.remove(type);
      } finally {
         writeLock.unlock();
      }

   }

   public void removeAllWithLock(@Nullable @NonnullElements ClassToInstanceMultiMap map) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.removeAll(map);
      } finally {
         writeLock.unlock();
      }

   }

   public void removeAllWithLock(@Nullable @NonnullElements Iterable removeValues) {
      Lock writeLock = this.getReadWriteLock().writeLock();

      try {
         writeLock.lock();
         this.removeAll(removeValues);
      } finally {
         writeLock.unlock();
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public Collection valuesWithLock() {
      Lock readLock = this.getReadWriteLock().readLock();

      Collection var2;
      try {
         readLock.lock();
         var2 = this.values();
      } finally {
         readLock.unlock();
      }

      return var2;
   }
}
