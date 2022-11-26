package javolution.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import javax.realtime.MemoryArea;
import javolution.lang.PersistentReference;
import javolution.lang.Reusable;
import javolution.realtime.RealtimeObject;

public class FastTable extends FastCollection implements List, Reusable, RandomAccess {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      public Object create() {
         return new FastTable();
      }

      public void cleanup(Object var1) {
         ((FastTable)var1).reset();
      }
   };
   private static final int D0 = 5;
   private static final int M0 = 31;
   private static final int C0 = 32;
   private static final int D1 = 7;
   private static final int R1 = 5;
   private static final int M1 = 127;
   private static final int C1 = 4096;
   private static final int D2 = 9;
   private static final int R2 = 12;
   private static final int M2 = 511;
   private static final int C2 = 2097152;
   private static final int D3 = 11;
   private static final int R3 = 21;
   private Object[][] _elems1;
   private Object[][][] _elems2;
   private Object[][][][] _elems3;
   private int _capacity;
   private int _size;
   private static final Object[] NULL_BLOCK = (Object[])(new Object[32]);

   public FastTable() {
      this._capacity = 32;
      this._elems1 = (Object[][])(new Object[1][]);
      this._elems1[0] = (Object[])(new Object[32]);
   }

   public FastTable(String var1) {
      this();
      PersistentReference var2 = new PersistentReference(var1);
      FastTable var3 = (FastTable)var2.get();
      if (var3 != null) {
         this.addAll(var3);
      }

      var2.set(this);
   }

   public FastTable(int var1) {
      this();

      while(var1 > this._capacity) {
         this.increaseCapacity();
      }

   }

   public FastTable(Collection var1) {
      this(var1.size());
      this.addAll(var1);
   }

   public static FastTable newInstance() {
      return (FastTable)FACTORY.object();
   }

   public final Object get(int var1) {
      return var1 >> 12 == 0 && var1 < this._size ? this._elems1[var1 >> 5][var1 & 31] : this.get2(var1);
   }

   private final Object get2(int var1) {
      if (var1 >= 0 && var1 < this._size) {
         return var1 < 2097152 ? this._elems2[var1 >> 12][var1 >> 5 & 127][var1 & 31] : this._elems3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127][var1 & 31];
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final Object set(int var1, Object var2) {
      if (var1 >= 0 && var1 < this._size) {
         Object[] var3 = var1 < 4096 ? this._elems1[var1 >> 5] : (var1 < 2097152 ? this._elems2[var1 >> 12][var1 >> 5 & 127] : this._elems3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127]);
         Object var4 = var3[var1 & 31];
         var3[var1 & 31] = var2;
         return var4;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final boolean add(Object var1) {
      int var2 = this._size;
      if (var2 >= this._capacity) {
         this.increaseCapacity();
      }

      Object[] var3 = var2 < 4096 ? this._elems1[var2 >> 5] : (var2 < 2097152 ? this._elems2[var2 >> 12][var2 >> 5 & 127] : this._elems3[var2 >> 21][var2 >> 12 & 511][var2 >> 5 & 127]);
      var3[var2 & 31] = var1;
      ++this._size;
      return true;
   }

   public final Object getFirst() {
      if (this._size == 0) {
         throw new NoSuchElementException();
      } else {
         return this._elems1[0][0];
      }
   }

   public final Object getLast() {
      if (this._size == 0) {
         throw new NoSuchElementException();
      } else {
         int var1 = this._size - 1;
         Object[] var2 = var1 < 4096 ? this._elems1[var1 >> 5] : (var1 < 2097152 ? this._elems2[var1 >> 12][var1 >> 5 & 127] : this._elems3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127]);
         return var2[var1 & 31];
      }
   }

   public final void addLast(Object var1) {
      this.add(var1);
   }

   public final Object removeLast() {
      if (this._size == 0) {
         throw new NoSuchElementException();
      } else {
         int var1 = --this._size;
         Object[] var2 = var1 < 4096 ? this._elems1[var1 >> 5] : (var1 < 2097152 ? this._elems2[var1 >> 12][var1 >> 5 & 127] : this._elems3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127]);
         Object var3 = var2[var1 & 31];
         var2[var1 & 31] = null;
         return var3;
      }
   }

   public final void clear() {
      int var1 = this._size;
      this._size = 0;
      int var2 = Math.min(var1, 32);

      for(int var3 = 0; var3 < var1; var3 += 32) {
         Object[] var4 = var3 < 4096 ? this._elems1[var3 >> 5] : (var3 < 2097152 ? this._elems2[var3 >> 12][var3 >> 5 & 127] : this._elems3[var3 >> 21][var3 >> 12 & 511][var3 >> 5 & 127]);
         System.arraycopy(NULL_BLOCK, 0, var4, 0, var2);
      }

   }

   public final boolean addAll(int var1, Collection var2) {
      if (var1 >= 0 && var1 <= this._size) {
         int var3 = var2.size();
         int var4 = this._size;
         int var5 = var4 + var3;

         while(var5 >= this._capacity) {
            this.increaseCapacity();
         }

         this._size = var5;
         int var6 = var4;

         while(true) {
            --var6;
            if (var6 < var1) {
               Iterator var9 = var2.iterator();
               int var7 = var1;

               for(int var8 = var1 + var3; var7 < var8; ++var7) {
                  this.set(var7, var9.next());
               }

               return var3 != 0;
            }

            this.set(var6 + var3, this.get(var6));
         }
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final void add(int var1, Object var2) {
      if (var1 >= 0 && var1 <= this._size) {
         int var3 = this._size;
         int var4 = var3 + 1;
         if (var4 >= this._capacity) {
            this.increaseCapacity();
         }

         this._size = var4;
         int var5 = var1;

         for(int var6 = var4; var5 < var6; var2 = this.set(var5++, var2)) {
         }

      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final Object remove(int var1) {
      if (var1 >= 0 && var1 < this._size) {
         int var2 = this._size - 1;
         Object var3 = this.get(var2);
         int var4 = var2;

         while(true) {
            --var4;
            if (var4 < var1) {
               this.set(var2, (Object)null);
               this._size = var2;
               return var3;
            }

            var3 = this.set(var4, var3);
         }
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final void removeRange(int var1, int var2) {
      int var3 = this._size;
      if (var1 >= 0 && var2 >= 0 && var1 <= var2 && var2 <= var3) {
         int var4 = var2;
         int var5 = var1;

         while(var4 < var3) {
            this.set(var5++, this.get(var4++));
         }

         var4 = var3 - var2 + var1;
         var5 = var4;

         while(var5 < var3) {
            this.set(var5++, (Object)null);
         }

         this._size = var4;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final int indexOf(Object var1) {
      FastComparator var2 = this.getValueComparator();
      int var3 = -1;

      do {
         ++var3;
         if (var3 >= this._size) {
            return -1;
         }
      } while(!var2.areEqual(var1, this.get(var3)));

      return var3;
   }

   public final int lastIndexOf(Object var1) {
      FastComparator var2 = this.getValueComparator();
      int var3 = this._size;

      do {
         --var3;
         if (var3 < 0) {
            return -1;
         }
      } while(!var2.areEqual(var1, this.get(var3)));

      return var3;
   }

   public final Iterator iterator() {
      FastTableIterator var1 = (FastTableIterator)FastTable.FastTableIterator.access$000().object();
      FastTable.FastTableIterator.access$102(var1, this);
      FastTable.FastTableIterator.access$202(var1, 0);
      FastTable.FastTableIterator.access$302(var1, this._size);
      FastTable.FastTableIterator.access$402(var1, 0);
      FastTable.FastTableIterator.access$502(var1, -1);
      return var1;
   }

   public final ListIterator listIterator() {
      FastTableIterator var1 = (FastTableIterator)FastTable.FastTableIterator.access$000().object();
      FastTable.FastTableIterator.access$102(var1, this);
      FastTable.FastTableIterator.access$202(var1, 0);
      FastTable.FastTableIterator.access$302(var1, this._size);
      FastTable.FastTableIterator.access$402(var1, 0);
      FastTable.FastTableIterator.access$502(var1, -1);
      return var1;
   }

   public final ListIterator listIterator(int var1) {
      if (var1 >= 0 && var1 <= this._size) {
         FastTableIterator var2 = (FastTableIterator)FastTable.FastTableIterator.access$000().object();
         FastTable.FastTableIterator.access$102(var2, this);
         FastTable.FastTableIterator.access$202(var2, 0);
         FastTable.FastTableIterator.access$302(var2, this._size);
         FastTable.FastTableIterator.access$402(var2, var1);
         FastTable.FastTableIterator.access$502(var2, -1);
         return var2;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1 + " for table of size: " + this._size);
      }
   }

   public final List subList(int var1, int var2) {
      if (var1 >= 0 && var2 <= this._size && var1 <= var2) {
         SubTable var3 = (SubTable)FastTable.SubTable.access$600().object();
         FastTable.SubTable.access$702(var3, this);
         FastTable.SubTable.access$802(var3, var1);
         FastTable.SubTable.access$902(var3, var2 - var1);
         return var3;
      } else {
         throw new IndexOutOfBoundsException("fromIndex: " + var1 + ", toIndex: " + var2 + " for list of size: " + this._size);
      }
   }

   public final void trimToSize() {
      while(this._capacity > this._size + 32) {
         this.decreaseCapacity();
      }

   }

   public final void sort() {
      if (this._size > 1) {
         this.quicksort(0, this._size - 1, this.getValueComparator());
      }

   }

   private void quicksort(int var1, int var2, FastComparator var3) {
      boolean var4 = false;
      if (var1 < var2) {
         int var5 = this.partition(var1, var2, var3);
         this.quicksort(var1, var5 - 1, var3);
         this.quicksort(var5 + 1, var2, var3);
      }

   }

   private int partition(int var1, int var2, FastComparator var3) {
      Object var6 = this.get(var1);
      int var4 = var1;
      int var5 = var2;

      while(true) {
         while(var3.compare(this.get(var4), var6) > 0 || var4 >= var2) {
            while(var3.compare(this.get(var5), var6) > 0 && var5 > var1) {
               --var5;
            }

            if (var4 < var5) {
               Object var7 = this.get(var4);
               this.set(var4, this.get(var5));
               this.set(var5, var7);
            }

            if (var5 <= var4) {
               this.set(var1, this.get(var5));
               this.set(var5, var6);
               return var5;
            }
         }

         ++var4;
      }
   }

   public final int size() {
      return this._size;
   }

   public final FastCollection.Record head() {
      return FastTable.Index.access$1000();
   }

   public final FastCollection.Record tail() {
      return (Index)FastTable.Index.access$1100().get(this._size);
   }

   public final Object valueOf(FastCollection.Record var1) {
      return this.get(FastTable.Index.access$1200((Index)var1));
   }

   public final void delete(FastCollection.Record var1) {
      this.remove(FastTable.Index.access$1200((Index)var1));
   }

   public void reset() {
      super.setValueComparator(FastComparator.DIRECT);
      this.clear();
   }

   public List unmodifiable() {
      return (List)super.unmodifiable();
   }

   private void increaseCapacity() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            int var1 = FastTable.access$1300(FastTable.this);
            FastTable.access$1312(FastTable.this, 32);
            if (var1 < 4096) {
               if (FastTable.access$1400(FastTable.this).length == 1) {
                  Object[][] var2 = (Object[][])(new Object[128][]);
                  var2[0] = FastTable.access$1400(FastTable.this)[0];
                  FastTable.access$1402(FastTable.this, var2);
               }

               FastTable.access$1400(FastTable.this)[var1 >> 5] = (Object[])(new Object[32]);
            } else if (var1 < 2097152) {
               if (FastTable.access$1500(FastTable.this) == null) {
                  FastTable.access$1502(FastTable.this, (Object[][][])(new Object[512][][]));
               }

               if (FastTable.access$1500(FastTable.this)[var1 >> 12] == null) {
                  FastTable.access$1500(FastTable.this)[var1 >> 12] = (Object[][])(new Object[128][]);
               }

               FastTable.access$1500(FastTable.this)[var1 >> 12][var1 >> 5 & 127] = (Object[])(new Object[32]);
            } else {
               if (FastTable.access$1600(FastTable.this) == null) {
                  FastTable.access$1602(FastTable.this, (Object[][][][])(new Object[11][][][]));
               }

               if (FastTable.access$1600(FastTable.this)[var1 >> 21] == null) {
                  FastTable.access$1600(FastTable.this)[var1 >> 21] = (Object[][][])(new Object[9][][]);
               }

               if (FastTable.access$1600(FastTable.this)[var1 >> 21][var1 >> 12 & 511] == null) {
                  FastTable.access$1600(FastTable.this)[var1 >> 21][var1 >> 12 & 511] = (Object[][])(new Object[7][]);
               }

               FastTable.access$1600(FastTable.this)[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127] = (Object[])(new Object[5]);
            }

            if (FastTable.access$1300(FastTable.this) >= FastTable.access$1700(FastTable.Index.access$1100()) && FastTable.this != FastTable.Index.access$1100()) {
               while(FastTable.access$1300(FastTable.this) >= FastTable.access$1700(FastTable.Index.access$1100())) {
                  FastTable.Index.access$1800();
               }
            }

         }
      });
   }

   private void decreaseCapacity() {
      if (this._size >= this._capacity - 32) {
         throw new IllegalStateException();
      } else {
         int var1 = this._capacity;
         this._capacity -= 32;
         if (var1 < 4096) {
            this._elems1[var1 >> 5] = null;
            this._elems2 = (Object[][][])null;
            this._elems3 = (Object[][][][])null;
         } else if (var1 < 2097152) {
            this._elems2[var1 >> 12][var1 >> 5 & 127] = null;
            this._elems3 = (Object[][][][])null;
         } else {
            this._elems3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127] = null;
         }

      }
   }

   public Collection unmodifiable() {
      return this.unmodifiable();
   }

   static int access$1300(FastTable var0) {
      return var0._capacity;
   }

   static int access$1312(FastTable var0, int var1) {
      return var0._capacity += var1;
   }

   static Object[][] access$1400(FastTable var0) {
      return var0._elems1;
   }

   static Object[][] access$1402(FastTable var0, Object[][] var1) {
      return var0._elems1 = var1;
   }

   static Object[][][] access$1500(FastTable var0) {
      return var0._elems2;
   }

   static Object[][][] access$1502(FastTable var0, Object[][][] var1) {
      return var0._elems2 = var1;
   }

   static Object[][][][] access$1600(FastTable var0) {
      return var0._elems3;
   }

   static Object[][][][] access$1602(FastTable var0, Object[][][][] var1) {
      return var0._elems3 = var1;
   }

   static int access$1700(FastTable var0) {
      return var0._size;
   }

   private static final class SubTable extends FastCollection implements List, RandomAccess {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         protected Object create() {
            return new SubTable();
         }

         protected void cleanup(Object var1) {
            SubTable var2 = (SubTable)var1;
            FastTable.SubTable.access$702(var2, (FastTable)null);
         }
      };
      private FastTable _table;
      private int _offset;
      private int _size;

      private SubTable() {
      }

      public int size() {
         return this._size;
      }

      public FastCollection.Record head() {
         return FastTable.Index.access$1000();
      }

      public FastCollection.Record tail() {
         return (Index)FastTable.Index.access$1100().get(this._size);
      }

      public Object valueOf(FastCollection.Record var1) {
         return this._table.get(FastTable.Index.access$1200((Index)var1) + this._offset);
      }

      public void delete(FastCollection.Record var1) {
         throw new UnsupportedOperationException("Deletion not supported, thread-safe collections.");
      }

      public boolean addAll(int var1, Collection var2) {
         throw new UnsupportedOperationException("Insertion not supported, thread-safe collections.");
      }

      public Object get(int var1) {
         if (var1 >= 0 && var1 < this._size) {
            return this._table.get(var1 + this._offset);
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public Object set(int var1, Object var2) {
         if (var1 >= 0 && var1 < this._size) {
            return this._table.set(var1 + this._offset, var2);
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public void add(int var1, Object var2) {
         throw new UnsupportedOperationException("Insertion not supported, thread-safe collections.");
      }

      public Object remove(int var1) {
         throw new UnsupportedOperationException("Deletion not supported, thread-safe collections.");
      }

      public int indexOf(Object var1) {
         FastComparator var2 = this._table.getValueComparator();
         int var3 = -1;

         do {
            ++var3;
            if (var3 >= this._size) {
               return -1;
            }
         } while(!var2.areEqual(var1, this._table.get(var3 + this._offset)));

         return var3;
      }

      public int lastIndexOf(Object var1) {
         FastComparator var2 = this._table.getValueComparator();
         int var3 = this._size;

         do {
            --var3;
            if (var3 < 0) {
               return -1;
            }
         } while(!var2.areEqual(var1, this._table.get(var3 + this._offset)));

         return var3;
      }

      public ListIterator listIterator() {
         return this.listIterator(0);
      }

      public ListIterator listIterator(int var1) {
         if (var1 >= 0 && var1 <= this._size) {
            FastTableIterator var2 = (FastTableIterator)FastTable.FastTableIterator.access$000().object();
            FastTable.FastTableIterator.access$102(var2, this._table);
            FastTable.FastTableIterator.access$202(var2, this._offset);
            FastTable.FastTableIterator.access$302(var2, this._offset + this._size);
            FastTable.FastTableIterator.access$402(var2, var1 + this._offset);
            return var2;
         } else {
            throw new IndexOutOfBoundsException("index: " + var1 + " for table of size: " + this._size);
         }
      }

      public List subList(int var1, int var2) {
         if (var1 >= 0 && var2 <= this._size && var1 <= var2) {
            SubTable var3 = (SubTable)FACTORY.object();
            var3._table = this._table;
            var3._offset = this._offset + var1;
            var3._size = var2 - var1;
            return var3;
         } else {
            throw new IndexOutOfBoundsException("fromIndex: " + var1 + ", toIndex: " + var2 + " for list of size: " + this._size);
         }
      }

      static RealtimeObject.Factory access$600() {
         return FACTORY;
      }

      static FastTable access$702(SubTable var0, FastTable var1) {
         return var0._table = var1;
      }

      static int access$802(SubTable var0, int var1) {
         return var0._offset = var1;
      }

      static int access$902(SubTable var0, int var1) {
         return var0._size = var1;
      }

      SubTable(Object var1) {
         this();
      }
   }

   private static final class FastTableIterator extends RealtimeObject implements ListIterator {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         protected Object create() {
            return new FastTableIterator();
         }

         protected void cleanup(Object var1) {
            FastTableIterator var2 = (FastTableIterator)var1;
            FastTable.FastTableIterator.access$102(var2, (FastTable)null);
         }
      };
      private FastTable _table;
      private int _currentIndex;
      private int _start;
      private int _end;
      private int _nextIndex;

      private FastTableIterator() {
      }

      public boolean hasNext() {
         return this._nextIndex != this._end;
      }

      public Object next() {
         if (this._nextIndex == this._end) {
            throw new NoSuchElementException();
         } else {
            return this._table.get(this._currentIndex = this._nextIndex++);
         }
      }

      public int nextIndex() {
         return this._nextIndex;
      }

      public boolean hasPrevious() {
         return this._nextIndex != this._start;
      }

      public Object previous() {
         if (this._nextIndex == this._start) {
            throw new NoSuchElementException();
         } else {
            return this._table.get(this._currentIndex = --this._nextIndex);
         }
      }

      public int previousIndex() {
         return this._nextIndex - 1;
      }

      public void add(Object var1) {
         this._table.add(this._nextIndex++, var1);
         ++this._end;
         this._currentIndex = -1;
      }

      public void set(Object var1) {
         if (this._currentIndex >= 0) {
            this._table.set(this._currentIndex, var1);
         } else {
            throw new IllegalStateException();
         }
      }

      public void remove() {
         if (this._currentIndex >= 0) {
            this._table.remove(this._currentIndex);
            --this._end;
            if (this._currentIndex < this._nextIndex) {
               --this._nextIndex;
            }

            this._currentIndex = -1;
         } else {
            throw new IllegalStateException();
         }
      }

      static RealtimeObject.Factory access$000() {
         return FACTORY;
      }

      static FastTable access$102(FastTableIterator var0, FastTable var1) {
         return var0._table = var1;
      }

      static int access$202(FastTableIterator var0, int var1) {
         return var0._start = var1;
      }

      static int access$302(FastTableIterator var0, int var1) {
         return var0._end = var1;
      }

      static int access$402(FastTableIterator var0, int var1) {
         return var0._nextIndex = var1;
      }

      static int access$502(FastTableIterator var0, int var1) {
         return var0._currentIndex = var1;
      }

      FastTableIterator(Object var1) {
         this();
      }
   }

   public static final class Index implements FastCollection.Record {
      private static final FastTable COLLECTION = new FastTable();
      private static final Index MINUS_ONE = new Index();
      private static Index CollectionLast;
      private int _position;
      private Index _next;
      private Index _previous;

      private Index() {
      }

      public static Index getInstance(int var0) {
         if (var0 == -1) {
            return MINUS_ONE;
         } else if (var0 < -1) {
            throw new IllegalArgumentException("position: Should be greater or equal to -1");
         } else {
            while(var0 >= COLLECTION.size()) {
               augment();
            }

            return (Index)COLLECTION.get(var0);
         }
      }

      private static void augment() {
         MemoryArea.getMemoryArea(COLLECTION).executeInArea(new Runnable() {
            public void run() {
               Index var1 = new Index();
               synchronized(FastTable.Index.access$1100()) {
                  FastTable.Index.access$1202(var1, FastTable.access$1700(FastTable.Index.access$1100()));
                  FastTable.Index.access$2102(FastTable.Index.access$2000(), var1);
                  FastTable.Index.access$2202(var1, FastTable.Index.access$2000());
                  FastTable.Index.access$1100().addLast(var1);
                  FastTable.Index.access$2002(var1);
               }
            }
         });
      }

      public final FastCollection.Record getNext() {
         return this._next;
      }

      public final FastCollection.Record getPrevious() {
         return this._previous;
      }

      static Index access$1000() {
         return MINUS_ONE;
      }

      static FastTable access$1100() {
         return COLLECTION;
      }

      static int access$1200(Index var0) {
         return var0._position;
      }

      static void access$1800() {
         augment();
      }

      Index(Object var1) {
         this();
      }

      static int access$1202(Index var0, int var1) {
         return var0._position = var1;
      }

      static Index access$2000() {
         return CollectionLast;
      }

      static Index access$2102(Index var0, Index var1) {
         return var0._next = var1;
      }

      static Index access$2202(Index var0, Index var1) {
         return var0._previous = var1;
      }

      static Index access$2002(Index var0) {
         CollectionLast = var0;
         return var0;
      }

      static {
         CollectionLast = MINUS_ONE;
         MINUS_ONE._position = -1;

         while(FastTable.access$1700(COLLECTION) <= 32) {
            augment();
         }

      }
   }
}
