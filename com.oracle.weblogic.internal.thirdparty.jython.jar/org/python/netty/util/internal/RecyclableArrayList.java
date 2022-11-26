package org.python.netty.util.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import org.python.netty.util.Recycler;

public final class RecyclableArrayList extends ArrayList {
   private static final long serialVersionUID = -8605125654176467947L;
   private static final int DEFAULT_INITIAL_CAPACITY = 8;
   private static final Recycler RECYCLER = new Recycler() {
      protected RecyclableArrayList newObject(Recycler.Handle handle) {
         return new RecyclableArrayList(handle);
      }
   };
   private boolean insertSinceRecycled;
   private final Recycler.Handle handle;

   public static RecyclableArrayList newInstance() {
      return newInstance(8);
   }

   public static RecyclableArrayList newInstance(int minCapacity) {
      RecyclableArrayList ret = (RecyclableArrayList)RECYCLER.get();
      ret.ensureCapacity(minCapacity);
      return ret;
   }

   private RecyclableArrayList(Recycler.Handle handle) {
      this(handle, 8);
   }

   private RecyclableArrayList(Recycler.Handle handle, int initialCapacity) {
      super(initialCapacity);
      this.handle = handle;
   }

   public boolean addAll(Collection c) {
      checkNullElements(c);
      if (super.addAll(c)) {
         this.insertSinceRecycled = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean addAll(int index, Collection c) {
      checkNullElements(c);
      if (super.addAll(index, c)) {
         this.insertSinceRecycled = true;
         return true;
      } else {
         return false;
      }
   }

   private static void checkNullElements(Collection c) {
      if (c instanceof RandomAccess && c instanceof List) {
         List list = (List)c;
         int size = list.size();

         for(int i = 0; i < size; ++i) {
            if (list.get(i) == null) {
               throw new IllegalArgumentException("c contains null values");
            }
         }
      } else {
         Iterator var1 = c.iterator();

         while(var1.hasNext()) {
            Object element = var1.next();
            if (element == null) {
               throw new IllegalArgumentException("c contains null values");
            }
         }
      }

   }

   public boolean add(Object element) {
      if (element == null) {
         throw new NullPointerException("element");
      } else if (super.add(element)) {
         this.insertSinceRecycled = true;
         return true;
      } else {
         return false;
      }
   }

   public void add(int index, Object element) {
      if (element == null) {
         throw new NullPointerException("element");
      } else {
         super.add(index, element);
         this.insertSinceRecycled = true;
      }
   }

   public Object set(int index, Object element) {
      if (element == null) {
         throw new NullPointerException("element");
      } else {
         Object old = super.set(index, element);
         this.insertSinceRecycled = true;
         return old;
      }
   }

   public boolean insertSinceRecycled() {
      return this.insertSinceRecycled;
   }

   public boolean recycle() {
      this.clear();
      this.insertSinceRecycled = false;
      this.handle.recycle(this);
      return true;
   }

   // $FF: synthetic method
   RecyclableArrayList(Recycler.Handle x0, Object x1) {
      this(x0);
   }
}
