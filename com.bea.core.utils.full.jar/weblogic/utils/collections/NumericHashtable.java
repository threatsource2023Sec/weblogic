package weblogic.utils.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Random;

public class NumericHashtable implements Cloneable, Serializable {
   private transient HashtableEntry[] table;
   private transient int count;
   private int threshold;
   private float loadFactor;

   public NumericHashtable(int initialCapacity, float loadFactor) {
      if (initialCapacity > 0 && !((double)loadFactor <= 0.0)) {
         this.loadFactor = loadFactor;
         this.table = new HashtableEntry[initialCapacity];
         this.threshold = (int)((float)initialCapacity * loadFactor);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public NumericHashtable(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public NumericHashtable() {
      this(101, 0.75F);
   }

   public int size() {
      return this.count;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public Enumeration keys() {
      return new HashtableEnumerator(this.table, true);
   }

   public Enumeration elements() {
      return new HashtableEnumerator(this.table, false);
   }

   public synchronized boolean contains(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         HashtableEntry[] tab = this.table;
         int i = tab.length;

         while(i-- > 0) {
            for(HashtableEntry e = tab[i]; e != null; e = e.next) {
               if (e.value.equals(value)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public synchronized boolean containsKey(long key) {
      HashtableEntry[] tab = this.table;
      int index = (int)((key & 2147483647L) % (long)tab.length);

      for(HashtableEntry e = tab[index]; e != null; e = e.next) {
         if (e.key == key) {
            return true;
         }
      }

      return false;
   }

   public synchronized Object get(long key) {
      HashtableEntry[] tab = this.table;
      int index = (int)((key & 2147483647L) % (long)tab.length);

      for(HashtableEntry e = tab[index]; e != null; e = e.next) {
         if (e.key == key) {
            return e.value;
         }
      }

      return null;
   }

   protected synchronized void rehash() {
      int oldCapacity = this.table.length;
      HashtableEntry[] oldTable = this.table;
      int newCapacity = oldCapacity * 2 + 1;
      HashtableEntry[] newTable = new HashtableEntry[newCapacity];
      this.threshold = (int)((float)newCapacity * this.loadFactor);
      this.table = newTable;
      int i = oldCapacity;

      HashtableEntry e;
      int index;
      while(i-- > 0) {
         for(HashtableEntry old = oldTable[i]; old != null; newTable[index] = e) {
            e = old;
            old = old.next;
            index = (int)((e.key & 2147483647L) % (long)newCapacity);
            e.next = newTable[index];
         }
      }

   }

   public synchronized Object put(long key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         HashtableEntry[] tab = this.table;
         int index = (int)((key & 2147483647L) % (long)tab.length);

         HashtableEntry e;
         for(e = tab[index]; e != null; e = e.next) {
            if (e.key == key) {
               Object old = e.value;
               e.value = value;
               return old;
            }
         }

         if (this.count >= this.threshold) {
            this.rehash();
            return this.put(key, value);
         } else {
            e = new HashtableEntry();
            e.key = key;
            e.value = value;
            e.next = tab[index];
            tab[index] = e;
            ++this.count;
            return null;
         }
      }
   }

   public synchronized Object remove(long key) {
      HashtableEntry[] tab = this.table;
      int index = (int)((key & 2147483647L) % (long)tab.length);
      HashtableEntry e = tab[index];

      for(HashtableEntry prev = null; e != null; e = e.next) {
         if (e.key == key) {
            if (prev != null) {
               prev.next = e.next;
            } else {
               tab[index] = e.next;
            }

            --this.count;
            return e.value;
         }

         prev = e;
      }

      return null;
   }

   public synchronized void clear() {
      HashtableEntry[] tab = this.table;
      int index = tab.length;

      while(true) {
         --index;
         if (index < 0) {
            this.count = 0;
            return;
         }

         tab[index] = null;
      }
   }

   public synchronized Object clone() {
      try {
         NumericHashtable t = (NumericHashtable)super.clone();
         t.table = new HashtableEntry[this.table.length];

         for(int i = this.table.length; i-- > 0; t.table[i] = this.table[i] != null ? (HashtableEntry)this.table[i].clone() : null) {
         }

         return t;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public String toString() {
      int max = this.size() - 1;
      StringBuffer buf = new StringBuffer();
      Enumeration k = this.keys();
      Enumeration e = this.elements();
      buf.append("{");

      for(int i = 0; i <= max; ++i) {
         String s1 = k.nextElement().toString();
         String s2 = e.nextElement().toString();
         buf.append(s1 + "=" + s2);
         if (i < max) {
            buf.append(", ");
         }
      }

      buf.append("}");
      return buf.toString();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeInt(this.table.length);
      s.writeInt(this.count);

      for(int index = this.table.length - 1; index >= 0; --index) {
         for(HashtableEntry entry = this.table[index]; entry != null; entry = entry.next) {
            s.writeLong(entry.key);
            s.writeObject(entry.value);
         }
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      int origlength = s.readInt();
      int elements = s.readInt();
      int length = (int)((float)elements * this.loadFactor) + elements / 20 + 3;
      if (length > elements && (length & 1) == 0) {
         --length;
      }

      if (origlength > 0 && length > origlength) {
         length = origlength;
      }

      this.table = new HashtableEntry[length];

      for(this.count = 0; elements > 0; --elements) {
         long key = s.readLong();
         Object value = s.readObject();
         this.put(key, value);
      }

   }

   public static void main(String[] a) {
      NumericHashtable t = new NumericHashtable();
      Random rand = new Random(System.currentTimeMillis());
      long[] ls = new long[10];

      int i;
      for(i = 0; i < ls.length; ++i) {
         ls[i] = rand.nextLong();
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
}
