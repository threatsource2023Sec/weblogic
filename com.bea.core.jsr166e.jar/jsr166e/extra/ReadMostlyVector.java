package jsr166e.extra;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import jsr166e.StampedLock;

public class ReadMostlyVector implements List, RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = 8673264195747942595L;
   private static final int MAX_ARRAY_SIZE = 2147483639;
   Object[] array;
   final StampedLock lock;
   int count;
   final int capacityIncrement;
   static final int INITIAL_CAP = 16;

   public ReadMostlyVector(int initialCapacity, int capacityIncrement) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
      } else {
         this.array = new Object[initialCapacity];
         this.capacityIncrement = capacityIncrement;
         this.lock = new StampedLock();
      }
   }

   public ReadMostlyVector(int initialCapacity) {
      this(initialCapacity, 0);
   }

   public ReadMostlyVector() {
      this.capacityIncrement = 0;
      this.lock = new StampedLock();
   }

   public ReadMostlyVector(Collection c) {
      Object[] elements = c.toArray();
      if (elements.getClass() != Object[].class) {
         elements = Arrays.copyOf(elements, elements.length, Object[].class);
      }

      this.array = elements;
      this.count = elements.length;
      this.capacityIncrement = 0;
      this.lock = new StampedLock();
   }

   ReadMostlyVector(Object[] array, int count, int capacityIncrement) {
      this.array = array;
      this.count = count;
      this.capacityIncrement = capacityIncrement;
      this.lock = new StampedLock();
   }

   final Object[] grow(int minCapacity) {
      Object[] items;
      int newCapacity;
      if ((items = this.array) == null) {
         newCapacity = 16;
      } else {
         int oldCapacity = this.array.length;
         newCapacity = oldCapacity + (this.capacityIncrement > 0 ? this.capacityIncrement : oldCapacity);
      }

      if (newCapacity - minCapacity < 0) {
         newCapacity = minCapacity;
      }

      if (newCapacity - 2147483639 > 0) {
         if (minCapacity < 0) {
            throw new OutOfMemoryError();
         }

         if (minCapacity > 2147483639) {
            newCapacity = Integer.MAX_VALUE;
         } else {
            newCapacity = 2147483639;
         }
      }

      return this.array = items == null ? new Object[newCapacity] : Arrays.copyOf(items, newCapacity);
   }

   static int findFirstIndex(Object[] items, Object x, int index, int fence) {
      int len;
      if (items != null && (len = items.length) > 0) {
         int start = index < 0 ? 0 : index;
         int bound = fence < len ? fence : len;
         int i = start;

         while(true) {
            if (i >= bound) {
               return -1;
            }

            Object e = items[i];
            if (x == null) {
               if (e == null) {
                  break;
               }
            } else if (x.equals(e)) {
               break;
            }

            ++i;
         }

         return i;
      } else {
         return -1;
      }
   }

   static int findLastIndex(Object[] items, Object x, int index, int origin) {
      int len;
      if (items != null && (len = items.length) > 0) {
         int last = index < len ? index : len - 1;
         int start = origin < 0 ? 0 : origin;
         int i = last;

         while(true) {
            if (i < start) {
               return -1;
            }

            Object e = items[i];
            if (x == null) {
               if (e == null) {
                  break;
               }
            } else if (x.equals(e)) {
               break;
            }

            --i;
         }

         return i;
      } else {
         return -1;
      }
   }

   final void rawAdd(Object e) {
      int n = this.count;
      Object[] items = this.array;
      if (items == null || n >= items.length) {
         items = this.grow(n + 1);
      }

      items[n] = e;
      this.count = n + 1;
   }

   final void rawAddAt(int index, Object e) {
      int n = this.count;
      Object[] items = this.array;
      if (index > n) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         if (items == null || n >= items.length) {
            items = this.grow(n + 1);
         }

         if (index < n) {
            System.arraycopy(items, index, items, index + 1, n - index);
         }

         items[index] = e;
         this.count = n + 1;
      }
   }

   final boolean rawAddAllAt(int index, Object[] elements) {
      int n = this.count;
      Object[] items = this.array;
      if (index >= 0 && index <= n) {
         int len = elements.length;
         if (len == 0) {
            return false;
         } else {
            int newCount = n + len;
            if (items == null || newCount >= items.length) {
               items = this.grow(newCount);
            }

            int mv = n - index;
            if (mv > 0) {
               System.arraycopy(items, index, items, index + len, mv);
            }

            System.arraycopy(elements, 0, items, index, len);
            this.count = newCount;
            return true;
         }
      } else {
         throw new ArrayIndexOutOfBoundsException(index);
      }
   }

   final boolean rawRemoveAt(int index) {
      int n = this.count - 1;
      Object[] items = this.array;
      if (items != null && index >= 0 && index <= n) {
         int mv = n - index;
         if (mv > 0) {
            System.arraycopy(items, index + 1, items, index, mv);
         }

         items[n] = null;
         this.count = n;
         return true;
      } else {
         return false;
      }
   }

   final boolean lockedRemoveAll(Collection c, int origin, int bound) {
      boolean removed = false;
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         int n = this.count;
         int fence = bound >= 0 && bound <= n ? bound : n;
         if (origin >= 0 && origin < fence) {
            Iterator i$ = c.iterator();

            while(i$.hasNext()) {
               for(Object x = i$.next(); this.rawRemoveAt(findFirstIndex(this.array, x, origin, fence)); removed = true) {
               }
            }
         }
      } finally {
         lock.unlockWrite(stamp);
      }

      return removed;
   }

   final boolean lockedRetainAll(Collection c, int origin, int bound) {
      StampedLock lock = this.lock;
      boolean removed = false;
      if (c != this) {
         long stamp = lock.writeLock();

         try {
            Object[] items;
            int n;
            if ((items = this.array) != null && (n = this.count) > 0 && n < items.length) {
               int i = origin;
               if (origin >= 0) {
                  int fence = bound >= 0 && bound <= n ? bound : n;

                  while(i < fence) {
                     if (c.contains(items[i])) {
                        ++i;
                     } else {
                        --fence;
                        --n;
                        int mv = n - i;
                        if (mv > 0) {
                           System.arraycopy(items, i + 1, items, i, mv);
                        }
                     }
                  }

                  if (this.count != n) {
                     this.count = n;
                     removed = true;
                  }
               }
            }
         } finally {
            lock.unlockWrite(stamp);
         }
      }

      return removed;
   }

   final void internalClear(int origin, int bound) {
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         int removed = fence - origin;
         int newCount = n - removed;
         int mv = n - (origin + removed);
         if (mv > 0) {
            System.arraycopy(items, origin + removed, items, origin, mv);
         }

         for(int i = n; i < newCount; ++i) {
            items[i] = null;
         }

         this.count = newCount;
      }

   }

   final boolean internalContainsAll(Collection c, int origin, int bound) {
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         Iterator i$ = c.iterator();

         while(i$.hasNext()) {
            Object e = i$.next();
            if (findFirstIndex(items, e, origin, fence) < 0) {
               return false;
            }
         }
      } else if (!c.isEmpty()) {
         return false;
      }

      return true;
   }

   final boolean internalEquals(List list, int origin, int bound) {
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         Iterator it = list.iterator();
         int i = origin;

         while(true) {
            if (i >= fence) {
               if (it.hasNext()) {
                  return false;
               }
               break;
            }

            if (!it.hasNext()) {
               return false;
            }

            Object y = it.next();
            Object x = items[i];
            if (x != y && (x == null || !x.equals(y))) {
               return false;
            }

            ++i;
         }
      } else if (!list.isEmpty()) {
         return false;
      }

      return true;
   }

   final int internalHashCode(int origin, int bound) {
      int hash = 1;
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;

         for(int i = origin; i < fence; ++i) {
            Object e = items[i];
            hash = 31 * hash + (e == null ? 0 : e.hashCode());
         }
      }

      return hash;
   }

   final String internalToString(int origin, int bound) {
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         int i = origin < 0 ? 0 : origin;
         if (i != fence) {
            StringBuilder sb = new StringBuilder();
            sb.append('[');

            while(true) {
               Object e = items[i];
               sb.append(e == this ? "(this Collection)" : e.toString());
               ++i;
               if (i >= fence) {
                  return sb.append(']').toString();
               }

               sb.append(',').append(' ');
            }
         }
      }

      return "[]";
   }

   final Object[] internalToArray(int origin, int bound) {
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         int i = origin < 0 ? 0 : origin;
         if (i != fence) {
            return Arrays.copyOfRange(items, i, fence, Object[].class);
         }
      }

      return new Object[0];
   }

   final Object[] internalToArray(Object[] a, int origin, int bound) {
      int alen = a.length;
      Object[] items;
      int len;
      if ((items = this.array) != null && (len = items.length) > 0) {
         if (origin < 0) {
            origin = 0;
         }

         int n;
         if ((n = this.count) > len) {
            n = len;
         }

         int fence = bound >= 0 && bound <= n ? bound : n;
         int i = origin < 0 ? 0 : origin;
         int rlen = fence - origin;
         if (rlen > 0) {
            if (alen >= rlen) {
               System.arraycopy(items, 0, a, origin, rlen);
               if (alen > rlen) {
                  a[rlen] = null;
               }

               return a;
            }

            return (Object[])Arrays.copyOfRange(items, i, fence, a.getClass());
         }
      }

      if (alen > 0) {
         a[0] = null;
      }

      return a;
   }

   public boolean add(Object e) {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         this.rawAdd(e);
      } finally {
         lock.unlockWrite(stamp);
      }

      return true;
   }

   public void add(int index, Object element) {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         this.rawAddAt(index, element);
      } finally {
         lock.unlockWrite(stamp);
      }

   }

   public boolean addAll(Collection c) {
      Object[] elements = c.toArray();
      int len = elements.length;
      if (len == 0) {
         return false;
      } else {
         StampedLock lock = this.lock;
         long stamp = lock.writeLock();

         try {
            Object[] items = this.array;
            int n = this.count;
            int newCount = n + len;
            if (items == null || newCount >= items.length) {
               items = this.grow(newCount);
            }

            System.arraycopy(elements, 0, items, n, len);
            this.count = newCount;
         } finally {
            lock.unlockWrite(stamp);
         }

         return true;
      }
   }

   public boolean addAll(int index, Collection c) {
      Object[] elements = c.toArray();
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      boolean ret;
      try {
         ret = this.rawAddAllAt(index, elements);
      } finally {
         lock.unlockWrite(stamp);
      }

      return ret;
   }

   public void clear() {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         int n = this.count;
         Object[] items = this.array;
         if (items != null) {
            for(int i = 0; i < n; ++i) {
               items[i] = null;
            }
         }

         this.count = 0;
      } finally {
         lock.unlockWrite(stamp);
      }

   }

   public boolean contains(Object o) {
      return this.indexOf(o, 0) >= 0;
   }

   public boolean containsAll(Collection c) {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      boolean ret;
      try {
         ret = this.internalContainsAll(c, 0, -1);
      } finally {
         lock.unlockRead(stamp);
      }

      return ret;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof List)) {
         return false;
      } else {
         StampedLock lock = this.lock;
         long stamp = lock.readLock();

         boolean var5;
         try {
            var5 = this.internalEquals((List)o, 0, -1);
         } finally {
            lock.unlockRead(stamp);
         }

         return var5;
      }
   }

   public Object get(int index) {
      StampedLock lock = this.lock;
      long stamp = lock.tryOptimisticRead();
      Object[] items;
      if (index >= 0 && (items = this.array) != null && index < this.count && index < items.length) {
         Object e = items[index];
         if (lock.validate(stamp)) {
            return e;
         }
      }

      return this.lockedGet(index);
   }

   private Object lockedGet(int index) {
      boolean oobe = false;
      Object e = null;
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         Object[] items;
         if ((items = this.array) != null && index < items.length && index < this.count && index >= 0) {
            e = items[index];
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockRead(stamp);
      }

      if (oobe) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         return e;
      }
   }

   public int hashCode() {
      StampedLock lock = this.lock;
      long s = lock.readLock();

      int h;
      try {
         h = this.internalHashCode(0, -1);
      } finally {
         lock.unlockRead(s);
      }

      return h;
   }

   public int indexOf(Object o) {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      int idx;
      try {
         idx = findFirstIndex(this.array, o, 0, this.count);
      } finally {
         lock.unlockRead(stamp);
      }

      return idx;
   }

   public boolean isEmpty() {
      StampedLock lock = this.lock;
      long stamp = lock.tryOptimisticRead();
      return this.count == 0;
   }

   public Iterator iterator() {
      return new Itr(this, 0);
   }

   public int lastIndexOf(Object o) {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      int idx;
      try {
         idx = findLastIndex(this.array, o, this.count - 1, 0);
      } finally {
         lock.unlockRead(stamp);
      }

      return idx;
   }

   public ListIterator listIterator() {
      return new Itr(this, 0);
   }

   public ListIterator listIterator(int index) {
      return new Itr(this, index);
   }

   public Object remove(int index) {
      Object oldValue = null;
      boolean oobe = false;
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         if (index >= 0 && index < this.count) {
            oldValue = this.array[index];
            this.rawRemoveAt(index);
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockWrite(stamp);
      }

      if (oobe) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         return oldValue;
      }
   }

   public boolean remove(Object o) {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      boolean var5;
      try {
         var5 = this.rawRemoveAt(findFirstIndex(this.array, o, 0, this.count));
      } finally {
         lock.unlockWrite(stamp);
      }

      return var5;
   }

   public boolean removeAll(Collection c) {
      return this.lockedRemoveAll(c, 0, -1);
   }

   public boolean retainAll(Collection c) {
      return this.lockedRetainAll(c, 0, -1);
   }

   public Object set(int index, Object element) {
      Object oldValue = null;
      boolean oobe = false;
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         Object[] items = this.array;
         if (items != null && index >= 0 && index < this.count) {
            oldValue = items[index];
            items[index] = element;
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockWrite(stamp);
      }

      if (oobe) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         return oldValue;
      }
   }

   public int size() {
      StampedLock lock = this.lock;
      long stamp = lock.tryOptimisticRead();
      return this.count;
   }

   private int lockedSize() {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      int n;
      try {
         n = this.count;
      } finally {
         lock.unlockRead(stamp);
      }

      return n;
   }

   public List subList(int fromIndex, int toIndex) {
      int ssize = toIndex - fromIndex;
      if (ssize >= 0 && fromIndex >= 0) {
         ReadMostlyVectorSublist ret = null;
         StampedLock lock = this.lock;
         long stamp = lock.readLock();

         try {
            if (toIndex <= this.count) {
               ret = new ReadMostlyVectorSublist(this, fromIndex, ssize);
            }
         } finally {
            lock.unlockRead(stamp);
         }

         if (ret != null) {
            return ret;
         }
      }

      throw new ArrayIndexOutOfBoundsException(fromIndex < 0 ? fromIndex : toIndex);
   }

   public Object[] toArray() {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      Object[] var4;
      try {
         var4 = this.internalToArray(0, -1);
      } finally {
         lock.unlockRead(stamp);
      }

      return var4;
   }

   public Object[] toArray(Object[] a) {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      Object[] var5;
      try {
         var5 = this.internalToArray(a, 0, -1);
      } finally {
         lock.unlockRead(stamp);
      }

      return var5;
   }

   public String toString() {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      String var4;
      try {
         var4 = this.internalToString(0, -1);
      } finally {
         lock.unlockRead(stamp);
      }

      return var4;
   }

   public boolean addIfAbsent(Object e) {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      boolean ret;
      try {
         if (findFirstIndex(this.array, e, 0, this.count) < 0) {
            this.rawAdd(e);
            ret = true;
         } else {
            ret = false;
         }
      } finally {
         lock.unlockWrite(stamp);
      }

      return ret;
   }

   public int addAllAbsent(Collection c) {
      int added = 0;
      Object[] cs = c.toArray();
      int clen = cs.length;
      if (clen != 0) {
         long stamp = this.lock.writeLock();

         try {
            for(int i = 0; i < clen; ++i) {
               Object e = cs[i];
               if (findFirstIndex(this.array, e, 0, this.count) < 0) {
                  this.rawAdd(e);
                  ++added;
               }
            }
         } finally {
            this.lock.unlockWrite(stamp);
         }
      }

      return added;
   }

   public Iterator snapshotIterator() {
      return new SnapshotIterator(this);
   }

   public void forEachReadOnly(Action action) {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         Object[] items;
         int len;
         int n;
         if ((items = this.array) != null && (len = items.length) > 0 && (n = this.count) <= len) {
            for(int i = 0; i < n; ++i) {
               Object e = items[i];
               action.apply(e);
            }
         }
      } finally {
         lock.unlockRead(stamp);
      }

   }

   public Object firstElement() {
      StampedLock lock = this.lock;
      long stamp = lock.tryOptimisticRead();
      Object[] items;
      if ((items = this.array) != null && this.count > 0 && items.length > 0) {
         Object e = items[0];
         if (lock.validate(stamp)) {
            return e;
         }
      }

      return this.lockedFirstElement();
   }

   private Object lockedFirstElement() {
      Object e = null;
      boolean oobe = false;
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         Object[] items = this.array;
         if (items != null && this.count > 0 && items.length > 0) {
            e = items[0];
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockRead(stamp);
      }

      if (oobe) {
         throw new NoSuchElementException();
      } else {
         return e;
      }
   }

   public Object lastElement() {
      StampedLock lock = this.lock;
      long stamp = lock.tryOptimisticRead();
      Object[] items;
      int i;
      if ((items = this.array) != null && (i = this.count - 1) >= 0 && i < items.length) {
         Object e = items[i];
         if (lock.validate(stamp)) {
            return e;
         }
      }

      return this.lockedLastElement();
   }

   private Object lockedLastElement() {
      Object e = null;
      boolean oobe = false;
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         Object[] items = this.array;
         int i = this.count - 1;
         if (items != null && i >= 0 && i < items.length) {
            e = items[i];
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockRead(stamp);
      }

      if (oobe) {
         throw new NoSuchElementException();
      } else {
         return e;
      }
   }

   public int indexOf(Object o, int index) {
      if (index < 0) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         StampedLock lock = this.lock;
         long stamp = lock.readLock();

         int idx;
         try {
            idx = findFirstIndex(this.array, o, index, this.count);
         } finally {
            lock.unlockRead(stamp);
         }

         return idx;
      }
   }

   public int lastIndexOf(Object o, int index) {
      boolean oobe = false;
      int idx = -1;
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         if (index < this.count) {
            idx = findLastIndex(this.array, o, index, 0);
         } else {
            oobe = true;
         }
      } finally {
         lock.unlockRead(stamp);
      }

      if (oobe) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         return idx;
      }
   }

   public void setSize(int newSize) {
      if (newSize < 0) {
         throw new ArrayIndexOutOfBoundsException(newSize);
      } else {
         StampedLock lock = this.lock;
         long stamp = lock.writeLock();

         try {
            int n = this.count;
            if (newSize > n) {
               this.grow(newSize);
            } else {
               Object[] items;
               if ((items = this.array) != null) {
                  for(int i = newSize; i < n; ++i) {
                     items[i] = null;
                  }
               }
            }

            this.count = newSize;
         } finally {
            lock.unlockWrite(stamp);
         }

      }
   }

   public void copyInto(Object[] anArray) {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         Object[] items;
         if ((items = this.array) != null) {
            System.arraycopy(items, 0, anArray, 0, this.count);
         }
      } finally {
         lock.unlockWrite(stamp);
      }

   }

   public void trimToSize() {
      StampedLock lock = this.lock;
      long stamp = lock.writeLock();

      try {
         Object[] items = this.array;
         int n = this.count;
         if (items != null && n < items.length) {
            this.array = Arrays.copyOf(items, n);
         }
      } finally {
         lock.unlockWrite(stamp);
      }

   }

   public void ensureCapacity(int minCapacity) {
      if (minCapacity > 0) {
         StampedLock lock = this.lock;
         long stamp = lock.writeLock();

         try {
            Object[] items = this.array;
            int cap = items == null ? 0 : items.length;
            if (minCapacity - cap > 0) {
               this.grow(minCapacity);
            }
         } finally {
            lock.unlockWrite(stamp);
         }
      }

   }

   public Enumeration elements() {
      return new Itr(this, 0);
   }

   public int capacity() {
      return this.array.length;
   }

   public Object elementAt(int index) {
      return this.get(index);
   }

   public void setElementAt(Object obj, int index) {
      this.set(index, obj);
   }

   public void removeElementAt(int index) {
      this.remove(index);
   }

   public void insertElementAt(Object obj, int index) {
      this.add(index, obj);
   }

   public void addElement(Object obj) {
      this.add(obj);
   }

   public boolean removeElement(Object obj) {
      return this.remove(obj);
   }

   public void removeAllElements() {
      this.clear();
   }

   public ReadMostlyVector clone() {
      Object[] a = null;
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      int n;
      try {
         Object[] items = this.array;
         if (items == null) {
            n = 0;
         } else {
            int len = items.length;
            if ((n = this.count) > len) {
               n = len;
            }

            a = Arrays.copyOf(items, n);
         }
      } finally {
         lock.unlockRead(stamp);
      }

      return new ReadMostlyVector(a, n, this.capacityIncrement);
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      StampedLock lock = this.lock;
      long stamp = lock.readLock();

      try {
         s.defaultWriteObject();
      } finally {
         lock.unlockRead(stamp);
      }

   }

   static final class SubItr implements ListIterator {
      final ReadMostlyVectorSublist sublist;
      final ReadMostlyVector list;
      final StampedLock lock;
      Object[] items;
      long seq;
      int cursor;
      int origin;
      int fence;
      int lastRet;

      SubItr(ReadMostlyVectorSublist sublist, int index) {
         StampedLock lock = sublist.list.lock;
         long stamp = lock.readLock();

         try {
            this.sublist = sublist;
            this.list = sublist.list;
            this.lock = lock;
            this.cursor = index;
            this.origin = sublist.offset;
            this.fence = this.origin + sublist.size;
            this.lastRet = -1;
         } finally {
            this.seq = lock.tryConvertToOptimisticRead(stamp);
         }

         if (index < 0 || this.cursor > this.fence) {
            throw new ArrayIndexOutOfBoundsException(index);
         }
      }

      public int nextIndex() {
         return this.cursor - this.origin;
      }

      public int previousIndex() {
         return this.cursor - this.origin - 1;
      }

      public boolean hasNext() {
         return this.cursor < this.fence;
      }

      public boolean hasPrevious() {
         return this.cursor > this.origin;
      }

      public Object next() {
         int i = this.cursor;
         Object[] es = this.items;
         if (es != null && i >= this.origin && i < this.fence && i < es.length) {
            Object e = es[i];
            this.lastRet = i;
            this.cursor = i + 1;
            if (!this.lock.validate(this.seq)) {
               throw new ConcurrentModificationException();
            } else {
               return e;
            }
         } else {
            throw new NoSuchElementException();
         }
      }

      public Object previous() {
         int i = this.cursor - 1;
         Object[] es = this.items;
         if (es != null && i >= 0 && i < this.fence && i < es.length) {
            Object e = es[i];
            this.lastRet = i;
            this.cursor = i;
            if (!this.lock.validate(this.seq)) {
               throw new ConcurrentModificationException();
            } else {
               return e;
            }
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         int i = this.lastRet;
         if (i < 0) {
            throw new IllegalStateException();
         } else if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
            throw new ConcurrentModificationException();
         } else {
            try {
               this.list.rawRemoveAt(i);
               this.fence = this.origin + this.sublist.size;
               this.cursor = i;
               this.lastRet = -1;
            } finally {
               this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
            }

         }
      }

      public void set(Object e) {
         int i = this.lastRet;
         if (i >= this.origin && i < this.fence) {
            if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
               throw new ConcurrentModificationException();
            } else {
               try {
                  this.list.set(i, e);
               } finally {
                  this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
               }

            }
         } else {
            throw new IllegalStateException();
         }
      }

      public void add(Object e) {
         int i = this.cursor;
         if (i >= this.origin && i < this.fence) {
            if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
               throw new ConcurrentModificationException();
            } else {
               try {
                  this.list.rawAddAt(i, e);
                  this.items = this.list.array;
                  this.fence = this.origin + this.sublist.size;
                  this.cursor = i + 1;
                  this.lastRet = -1;
               } finally {
                  this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
               }

            }
         } else {
            throw new IllegalStateException();
         }
      }
   }

   static final class ReadMostlyVectorSublist implements List, RandomAccess, Serializable {
      private static final long serialVersionUID = 3041673470172026059L;
      final ReadMostlyVector list;
      final int offset;
      volatile int size;

      ReadMostlyVectorSublist(ReadMostlyVector list, int offset, int size) {
         this.list = list;
         this.offset = offset;
         this.size = size;
      }

      private void rangeCheck(int index) {
         if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException(index);
         }
      }

      public boolean add(Object element) {
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         try {
            int c = this.size;
            this.list.rawAddAt(c + this.offset, element);
            this.size = c + 1;
         } finally {
            lock.unlockWrite(stamp);
         }

         return true;
      }

      public void add(int index, Object element) {
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         try {
            if (index < 0 || index > this.size) {
               throw new ArrayIndexOutOfBoundsException(index);
            }

            this.list.rawAddAt(index + this.offset, element);
            ++this.size;
         } finally {
            lock.unlockWrite(stamp);
         }

      }

      public boolean addAll(Collection c) {
         Object[] elements = c.toArray();
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         boolean var9;
         try {
            int s = this.size;
            int pc = this.list.count;
            this.list.rawAddAllAt(this.offset + s, elements);
            int added = this.list.count - pc;
            this.size = s + added;
            var9 = added != 0;
         } finally {
            lock.unlockWrite(stamp);
         }

         return var9;
      }

      public boolean addAll(int index, Collection c) {
         Object[] elements = c.toArray();
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         boolean var10;
         try {
            int s = this.size;
            if (index < 0 || index > s) {
               throw new ArrayIndexOutOfBoundsException(index);
            }

            int pc = this.list.count;
            this.list.rawAddAllAt(index + this.offset, elements);
            int added = this.list.count - pc;
            this.size = s + added;
            var10 = added != 0;
         } finally {
            lock.unlockWrite(stamp);
         }

         return var10;
      }

      public void clear() {
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         try {
            this.list.internalClear(this.offset, this.offset + this.size);
            this.size = 0;
         } finally {
            lock.unlockWrite(stamp);
         }

      }

      public boolean contains(Object o) {
         return this.indexOf(o) >= 0;
      }

      public boolean containsAll(Collection c) {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         boolean var5;
         try {
            var5 = this.list.internalContainsAll(c, this.offset, this.offset + this.size);
         } finally {
            lock.unlockRead(stamp);
         }

         return var5;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof List)) {
            return false;
         } else {
            StampedLock lock = this.list.lock;
            long stamp = lock.readLock();

            boolean var5;
            try {
               var5 = this.list.internalEquals((List)((List)o), this.offset, this.offset + this.size);
            } finally {
               lock.unlockRead(stamp);
            }

            return var5;
         }
      }

      public Object get(int index) {
         if (index >= 0 && index < this.size) {
            return this.list.get(index + this.offset);
         } else {
            throw new ArrayIndexOutOfBoundsException(index);
         }
      }

      public int hashCode() {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         int var4;
         try {
            var4 = this.list.internalHashCode(this.offset, this.offset + this.size);
         } finally {
            lock.unlockRead(stamp);
         }

         return var4;
      }

      public int indexOf(Object o) {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         int var6;
         try {
            int idx = ReadMostlyVector.findFirstIndex(this.list.array, o, this.offset, this.offset + this.size);
            var6 = idx < 0 ? -1 : idx - this.offset;
         } finally {
            lock.unlockRead(stamp);
         }

         return var6;
      }

      public boolean isEmpty() {
         return this.size() == 0;
      }

      public Iterator iterator() {
         return new SubItr(this, this.offset);
      }

      public int lastIndexOf(Object o) {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         int var6;
         try {
            int idx = ReadMostlyVector.findLastIndex(this.list.array, o, this.offset + this.size - 1, this.offset);
            var6 = idx < 0 ? -1 : idx - this.offset;
         } finally {
            lock.unlockRead(stamp);
         }

         return var6;
      }

      public ListIterator listIterator() {
         return new SubItr(this, this.offset);
      }

      public ListIterator listIterator(int index) {
         return new SubItr(this, index + this.offset);
      }

      public Object remove(int index) {
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         Object var8;
         try {
            Object[] items = this.list.array;
            int i = index + this.offset;
            if (items == null || index < 0 || index >= this.size || i >= items.length) {
               throw new ArrayIndexOutOfBoundsException(index);
            }

            Object result = items[i];
            this.list.rawRemoveAt(i);
            --this.size;
            var8 = result;
         } finally {
            lock.unlockWrite(stamp);
         }

         return var8;
      }

      public boolean remove(Object o) {
         StampedLock lock = this.list.lock;
         long stamp = lock.writeLock();

         boolean var5;
         try {
            if (this.list.rawRemoveAt(ReadMostlyVector.findFirstIndex(this.list.array, o, this.offset, this.offset + this.size))) {
               --this.size;
               var5 = true;
               return var5;
            }

            var5 = false;
         } finally {
            lock.unlockWrite(stamp);
         }

         return var5;
      }

      public boolean removeAll(Collection c) {
         return this.list.lockedRemoveAll(c, this.offset, this.offset + this.size);
      }

      public boolean retainAll(Collection c) {
         return this.list.lockedRetainAll(c, this.offset, this.offset + this.size);
      }

      public Object set(int index, Object element) {
         if (index >= 0 && index < this.size) {
            return this.list.set(index + this.offset, element);
         } else {
            throw new ArrayIndexOutOfBoundsException(index);
         }
      }

      public int size() {
         return this.size;
      }

      public List subList(int fromIndex, int toIndex) {
         int c = this.size;
         int ssize = toIndex - fromIndex;
         if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
         } else if (toIndex <= c && ssize >= 0) {
            return new ReadMostlyVectorSublist(this.list, this.offset + fromIndex, ssize);
         } else {
            throw new ArrayIndexOutOfBoundsException(toIndex);
         }
      }

      public Object[] toArray() {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         Object[] var4;
         try {
            var4 = this.list.internalToArray(this.offset, this.offset + this.size);
         } finally {
            lock.unlockRead(stamp);
         }

         return var4;
      }

      public Object[] toArray(Object[] a) {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         Object[] var5;
         try {
            var5 = this.list.internalToArray(a, this.offset, this.offset + this.size);
         } finally {
            lock.unlockRead(stamp);
         }

         return var5;
      }

      public String toString() {
         StampedLock lock = this.list.lock;
         long stamp = lock.readLock();

         String var4;
         try {
            var4 = this.list.internalToString(this.offset, this.offset + this.size);
         } finally {
            lock.unlockRead(stamp);
         }

         return var4;
      }
   }

   static final class Itr implements ListIterator, Enumeration {
      final StampedLock lock;
      final ReadMostlyVector list;
      Object[] items;
      long seq;
      int cursor;
      int fence;
      int lastRet;

      Itr(ReadMostlyVector list, int index) {
         StampedLock lock = list.lock;
         long stamp = lock.readLock();

         try {
            this.list = list;
            this.lock = lock;
            this.items = list.array;
            this.fence = list.count;
            this.cursor = index;
            this.lastRet = -1;
         } finally {
            this.seq = lock.tryConvertToOptimisticRead(stamp);
         }

         if (index < 0 || index > this.fence) {
            throw new ArrayIndexOutOfBoundsException(index);
         }
      }

      public boolean hasPrevious() {
         return this.cursor > 0;
      }

      public int nextIndex() {
         return this.cursor;
      }

      public int previousIndex() {
         return this.cursor - 1;
      }

      public boolean hasNext() {
         return this.cursor < this.fence;
      }

      public Object next() {
         int i = this.cursor;
         Object[] es = this.items;
         if (es != null && i >= 0 && i < this.fence && i < es.length) {
            Object e = es[i];
            this.lastRet = i;
            this.cursor = i + 1;
            if (!this.lock.validate(this.seq)) {
               throw new ConcurrentModificationException();
            } else {
               return e;
            }
         } else {
            throw new NoSuchElementException();
         }
      }

      public Object previous() {
         int i = this.cursor - 1;
         Object[] es = this.items;
         if (es != null && i >= 0 && i < this.fence && i < es.length) {
            Object e = es[i];
            this.lastRet = i;
            this.cursor = i;
            if (!this.lock.validate(this.seq)) {
               throw new ConcurrentModificationException();
            } else {
               return e;
            }
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         int i = this.lastRet;
         if (i < 0) {
            throw new IllegalStateException();
         } else if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
            throw new ConcurrentModificationException();
         } else {
            try {
               this.list.rawRemoveAt(i);
               this.fence = this.list.count;
               this.cursor = i;
               this.lastRet = -1;
            } finally {
               this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
            }

         }
      }

      public void set(Object e) {
         int i = this.lastRet;
         Object[] es = this.items;
         if (es != null && !(i < 0 | i >= this.fence)) {
            if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
               throw new ConcurrentModificationException();
            } else {
               try {
                  es[i] = e;
               } finally {
                  this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
               }

            }
         } else {
            throw new IllegalStateException();
         }
      }

      public void add(Object e) {
         int i = this.cursor;
         if (i < 0) {
            throw new IllegalStateException();
         } else if ((this.seq = this.lock.tryConvertToWriteLock(this.seq)) == 0L) {
            throw new ConcurrentModificationException();
         } else {
            try {
               this.list.rawAddAt(i, e);
               this.items = this.list.array;
               this.fence = this.list.count;
               this.cursor = i + 1;
               this.lastRet = -1;
            } finally {
               this.seq = this.lock.tryConvertToOptimisticRead(this.seq);
            }

         }
      }

      public boolean hasMoreElements() {
         return this.hasNext();
      }

      public Object nextElement() {
         return this.next();
      }
   }

   public interface Action {
      void apply(Object var1);
   }

   static final class SnapshotIterator implements Iterator {
      private final Object[] items;
      private int cursor;

      SnapshotIterator(ReadMostlyVector v) {
         this.items = v.toArray();
      }

      public boolean hasNext() {
         return this.cursor < this.items.length;
      }

      public Object next() {
         if (this.cursor < this.items.length) {
            return this.items[this.cursor++];
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
