package weblogic.utils.collections;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Iterator;

public final class SlabPool extends AbstractCollection implements Pool {
   private final Pool slabs;
   private final int slabSize;
   private final ThreadLocal threadSlab = new ThreadLocal();

   public SlabPool(int num, int size) {
      this.slabs = new StackPool(num);
      this.slabSize = size;
   }

   public int size() {
      Slab slab = (Slab)this.threadSlab.get();
      return slab == null ? 0 : slab.pointer;
   }

   public Iterator iterator() {
      Slab slab = (Slab)this.threadSlab.get();
      return (Iterator)(slab == null ? Collections.EMPTY_SET.iterator() : new FilteringIterator(new ArrayIterator(slab.values)) {
         protected boolean accept(Object o) {
            return o != null;
         }
      });
   }

   public boolean add(Object x) {
      Slab slab = (Slab)this.threadSlab.get();
      if (slab != null && slab.pointer == this.slabSize) {
         this.slabs.add(slab);
         slab = null;
      }

      if (slab == null) {
         slab = new Slab(this.slabSize);
         this.threadSlab.set(slab);
      }

      return slab.add(x);
   }

   public Object remove() {
      Slab slab = (Slab)this.threadSlab.get();
      if (slab == null) {
         slab = (Slab)this.slabs.remove();
      }

      return slab == null ? null : slab.remove();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   private static final class Slab {
      final int size;
      final Object[] values;
      int pointer;

      Slab(int s) {
         if (s < 0) {
            throw new IllegalArgumentException();
         } else {
            this.size = s;
            this.values = new Object[this.size];
            this.pointer = 0;
         }
      }

      public boolean add(Object o) {
         if (this.pointer == this.size) {
            return false;
         } else {
            this.values[this.pointer++] = o;
            return true;
         }
      }

      public Object remove() {
         if (this.pointer > 0) {
            Object o = this.values[--this.pointer];
            this.values[this.pointer] = null;
            return o;
         } else {
            return null;
         }
      }
   }
}
