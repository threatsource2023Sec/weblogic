package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jboss.weld.util.Preconditions;

public final class ImmutableHashSet extends ImmutableSet implements Serializable {
   private static final int MAX_CAPACITY = 30;
   private static final float LOAD_FACTOR = 0.75F;
   private static final int MAX_SIZE = (int)Math.floor(8.05306368E8);
   private static final long serialVersionUID = 1L;
   private final Object[] table;
   private final int size;
   private final int hashCode;

   public ImmutableHashSet(Set data) {
      Preconditions.checkNotNull(data);
      Preconditions.checkArgument(!data.isEmpty(), (Object)data);
      Preconditions.checkArgument(data.size() < MAX_SIZE, "Collection too large: " + data.size());
      this.size = data.size();
      this.table = new Object[tableSize(this.size)];
      Iterator var2 = data.iterator();

      while(var2.hasNext()) {
         Object element = var2.next();
         this.storeElement(element);
      }

      this.hashCode = data.hashCode();
   }

   private static int tableSize(int dataSize) {
      int candidate = Integer.highestOneBit(dataSize) << 1;
      return (float)candidate * 0.75F < (float)dataSize ? Integer.highestOneBit(dataSize) << 2 : candidate;
   }

   private int getTableIndex(int hashCode) {
      return hashCode & this.table.length - 1;
   }

   private void storeElement(Object element) {
      int i = element.hashCode();

      while(true) {
         int index = this.getTableIndex(i);
         if (this.table[index] == null) {
            this.table[index] = element;
            return;
         }

         ++i;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean contains(Object o) {
      if (o == null) {
         return false;
      } else {
         int i = o.hashCode();

         while(true) {
            Object item = this.table[this.getTableIndex(i)];
            if (item == null) {
               return false;
            }

            if (o.equals(item)) {
               return true;
            }

            ++i;
         }
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof AbstractImmutableSet) {
         AbstractImmutableSet that = (AbstractImmutableSet)obj;
         return this.hashCode() != that.hashCode() ? false : this.equalsSet(that);
      } else {
         return obj instanceof Set ? this.equalsSet((Set)obj) : false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public Iterator iterator() {
      return new IteratorImpl();
   }

   private class IteratorImpl implements Iterator {
      private int position;
      private int processedElements;

      private IteratorImpl() {
         this.position = -1;
         this.processedElements = 0;
      }

      public boolean hasNext() {
         return this.processedElements < ImmutableHashSet.this.size;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            ++this.position;

            while(ImmutableHashSet.this.table[this.position] == null) {
               ++this.position;
            }

            ++this.processedElements;
            return ImmutableHashSet.this.table[this.position];
         }
      }

      // $FF: synthetic method
      IteratorImpl(Object x1) {
         this();
      }
   }
}
