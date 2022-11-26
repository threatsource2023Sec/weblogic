package org.python.netty.handler.codec;

import java.util.AbstractList;
import java.util.RandomAccess;
import org.python.netty.util.Recycler;
import org.python.netty.util.internal.ObjectUtil;

final class CodecOutputList extends AbstractList implements RandomAccess {
   private static final Recycler RECYCLER = new Recycler() {
      protected CodecOutputList newObject(Recycler.Handle handle) {
         return new CodecOutputList(handle);
      }
   };
   private final Recycler.Handle handle;
   private int size;
   private Object[] array;
   private boolean insertSinceRecycled;

   static CodecOutputList newInstance() {
      return (CodecOutputList)RECYCLER.get();
   }

   private CodecOutputList(Recycler.Handle handle) {
      this.array = new Object[16];
      this.handle = handle;
   }

   public Object get(int index) {
      this.checkIndex(index);
      return this.array[index];
   }

   public int size() {
      return this.size;
   }

   public boolean add(Object element) {
      ObjectUtil.checkNotNull(element, "element");

      try {
         this.insert(this.size, element);
      } catch (IndexOutOfBoundsException var3) {
         this.expandArray();
         this.insert(this.size, element);
      }

      ++this.size;
      return true;
   }

   public Object set(int index, Object element) {
      ObjectUtil.checkNotNull(element, "element");
      this.checkIndex(index);
      Object old = this.array[index];
      this.insert(index, element);
      return old;
   }

   public void add(int index, Object element) {
      ObjectUtil.checkNotNull(element, "element");
      this.checkIndex(index);
      if (this.size == this.array.length) {
         this.expandArray();
      }

      if (index != this.size - 1) {
         System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
      }

      this.insert(index, element);
      ++this.size;
   }

   public Object remove(int index) {
      this.checkIndex(index);
      Object old = this.array[index];
      int len = this.size - index - 1;
      if (len > 0) {
         System.arraycopy(this.array, index + 1, this.array, index, len);
      }

      this.array[--this.size] = null;
      return old;
   }

   public void clear() {
      this.size = 0;
   }

   boolean insertSinceRecycled() {
      return this.insertSinceRecycled;
   }

   void recycle() {
      for(int i = 0; i < this.size; ++i) {
         this.array[i] = null;
      }

      this.clear();
      this.insertSinceRecycled = false;
      this.handle.recycle(this);
   }

   Object getUnsafe(int index) {
      return this.array[index];
   }

   private void checkIndex(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException();
      }
   }

   private void insert(int index, Object element) {
      this.array[index] = element;
      this.insertSinceRecycled = true;
   }

   private void expandArray() {
      int newCapacity = this.array.length << 1;
      if (newCapacity < 0) {
         throw new OutOfMemoryError();
      } else {
         Object[] newArray = new Object[newCapacity];
         System.arraycopy(this.array, 0, newArray, 0, this.array.length);
         this.array = newArray;
      }
   }

   // $FF: synthetic method
   CodecOutputList(Recycler.Handle x0, Object x1) {
      this(x0);
   }
}
