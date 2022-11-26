package weblogic.corba.utils;

import java.util.Random;

public final class IndirectionHashtable {
   private IndirectionTableEntry table;
   private IndirectionTableEntry tail;
   private int count;
   private final int tableSize;
   private static final int DEFAULT_TABLE_SIZE = 16;
   private static final Object NULL_OBJECT = new Object();

   public IndirectionHashtable(int tableSize) {
      if (tableSize <= 0) {
         throw new IllegalArgumentException();
      } else {
         this.tableSize = tableSize;
         this.table = this.tail = new IndirectionTableEntry(tableSize);
      }
   }

   public IndirectionHashtable() {
      this(16);
   }

   public boolean isEmpty() {
      return this.table.count == 0;
   }

   public Object get(int key) {
      IndirectionTableEntry curr = this.table;
      if (curr.count == 0) {
         return null;
      } else {
         while(curr != null && curr.positions[curr.count - 1] < key) {
            curr = curr.next;
         }

         if (curr == null) {
            return null;
         } else {
            int lower = 0;
            int upper = curr.count - 1;

            while(curr.positions[lower] < key && key < curr.positions[upper]) {
               int index = (upper - lower) / 2;
               if (index == 0) {
                  return null;
               }

               if (key < curr.positions[lower + index]) {
                  upper = lower + index;
               } else {
                  lower += index;
               }
            }

            Object ret = null;
            if (curr.positions[lower] == key) {
               ret = curr.values[lower];
            } else {
               if (curr.positions[upper] != key) {
                  return null;
               }

               ret = curr.values[upper];
            }

            return ret == NULL_OBJECT ? null : ret;
         }
      }
   }

   public Object remove(int key) {
      IndirectionTableEntry curr = this.table;
      if (curr.count == 0) {
         return null;
      } else if (this.tail.positions[this.tail.count - 1] == key) {
         --this.tail.count;
         Object tmp = this.tail.values[this.tail.count];
         this.tail.values[this.tail.count] = null;
         this.tail.positions[this.tail.count] = 0;
         if (this.tail.count == 0 && curr != this.tail) {
            while(curr.next != this.tail) {
               curr = curr.next;
            }

            this.tail = curr;
            curr.next = null;
         }

         return tmp;
      } else {
         while(curr != null && curr.positions[curr.count - 1] < key) {
            curr = curr.next;
         }

         if (curr == null) {
            return null;
         } else {
            int lower = 0;
            int upper = curr.count - 1;

            while(curr.positions[lower] < key && key < curr.positions[upper]) {
               int index = (upper - lower) / 2;
               if (index == 0) {
                  return null;
               }

               if (key < curr.positions[lower + index]) {
                  upper = lower + index;
               } else {
                  lower += index;
               }
            }

            Object ret = null;
            if (curr.positions[lower] == key) {
               ret = curr.values[lower];
               curr.values[lower] = null;
            } else if (curr.positions[upper] == key) {
               ret = curr.values[upper];
               curr.values[upper] = null;
            }

            return ret == NULL_OBJECT ? null : ret;
         }
      }
   }

   public Object put(int key, Object value) {
      if (value == null) {
         value = NULL_OBJECT;
      }

      if (this.tail.count > 0 && key < this.tail.positions[this.tail.count - 1]) {
         throw new IllegalArgumentException("Out of order key: " + key + " " + this.toString());
      } else {
         if (this.tail.count >= this.tableSize) {
            this.tail.next = new IndirectionTableEntry(this.tableSize);
            this.tail.next.prev = this.tail;
            this.tail = this.tail.next;
         }

         this.tail.positions[this.tail.count] = key;
         this.tail.values[this.tail.count++] = value;
         return value;
      }
   }

   public int reserve(int key) {
      if (this.tail.count > 0 && key < this.tail.positions[this.tail.count - 1]) {
         throw new IllegalArgumentException("Out of order key: " + key + " " + this.toString());
      } else {
         if (this.tail.count >= this.tableSize) {
            this.tail.next = new IndirectionTableEntry(this.tableSize);
            this.tail.next.prev = this.tail;
            this.tail = this.tail.next;
         }

         this.tail.positions[this.tail.count] = key;
         this.tail.values[this.tail.count++] = null;
         return this.tail.count - 1;
      }
   }

   public void putReserved(int index, int key, Object value) {
      if (value == null) {
         value = NULL_OBJECT;
      }

      IndirectionTableEntry curr;
      for(curr = this.tail; curr.count < index || curr.positions[index] != key; curr = curr.prev) {
      }

      if (curr.count >= index && curr.positions[index] == key) {
         curr.values[index] = value;
      } else {
         throw new IllegalArgumentException("No reserved slot for: " + index);
      }
   }

   public void clear() {
      this.table.next = null;
      this.tail = this.table;

      for(int index = 0; index < this.table.count; ++index) {
         this.table.positions[index] = 0;
         this.table.values[index] = null;
      }

      this.table.count = 0;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("{");

      for(IndirectionTableEntry curr = this.table; curr != null; curr = curr.next) {
         for(int i = 0; i < curr.count; ++i) {
            buf.append("" + curr.positions[i] + "=" + curr.values[i]);
            buf.append(", ");
         }
      }

      buf.append("}");
      return buf.toString();
   }

   public static void main(String[] a) {
      IndirectionHashtable t = new IndirectionHashtable();
      Random rand = new Random(System.currentTimeMillis());
      int seed = rand.nextInt();
      int[] ls = new int[63];

      int i;
      for(i = 0; i < ls.length; ++i) {
         ls[i] = seed + i * 3;
         Object val = String.valueOf(ls[i]);
         t.put(ls[i], val);
         System.out.println("put: " + ls[i] + ", '" + val + "'");
      }

      System.out.println("TABLE: \n" + t);

      for(i = 0; i < ls.length; ++i) {
         Object o = t.get(ls[i]);
         if (o == null) {
            System.err.println("not found: " + ls[i]);
         } else if (!o.equals(String.valueOf(ls[i]))) {
            System.err.println(o + "!=" + ls[i]);
         } else {
            System.out.println("OK: " + o);
         }
      }

   }

   static final class IndirectionTableEntry {
      int count = 0;
      final int[] positions;
      final Object[] values;
      IndirectionTableEntry next;
      IndirectionTableEntry prev;

      IndirectionTableEntry(int tableSize) {
         this.positions = new int[tableSize];
         this.values = new Object[tableSize];
      }
   }
}
