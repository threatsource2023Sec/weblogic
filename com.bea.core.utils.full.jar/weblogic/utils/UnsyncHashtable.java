package weblogic.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/** @deprecated */
@Deprecated
public final class UnsyncHashtable extends Dictionary implements Cloneable, Externalizable {
   private transient UnsyncHTEntry[] table;
   private transient int count;
   private int threshold;
   private float loadFactor;
   private static final long serialVersionUID = 1421746759512286392L;

   public UnsyncHashtable(int initialCapacity, float loadFactor) {
      if (initialCapacity > 0 && !((double)loadFactor <= 0.0)) {
         this.loadFactor = loadFactor;
         this.table = new UnsyncHTEntry[initialCapacity];
         this.threshold = (int)((float)initialCapacity * loadFactor);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public UnsyncHashtable(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public UnsyncHashtable() {
      this(101, 0.75F);
   }

   public int size() {
      return this.count;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public Enumeration keys() {
      return new UnsyncHTEnumerator(this.table, true);
   }

   public Enumeration elements() {
      return new UnsyncHTEnumerator(this.table, false);
   }

   public boolean contains(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         UnsyncHTEntry[] tab = this.table;
         int i = tab.length;

         while(i-- > 0) {
            for(UnsyncHTEntry e = tab[i]; e != null; e = e.next) {
               if (e.value.equals(value)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean containsKey(Object key) {
      UnsyncHTEntry[] tab = this.table;
      int hash = key.hashCode();
      int index = (hash & Integer.MAX_VALUE) % tab.length;

      for(UnsyncHTEntry e = tab[index]; e != null; e = e.next) {
         if (e.hash == hash && e.key.equals(key)) {
            return true;
         }
      }

      return false;
   }

   public Object get(Object key) {
      if (key == null) {
         return null;
      } else {
         UnsyncHTEntry[] tab = this.table;
         int hash = key.hashCode();
         int index = (hash & Integer.MAX_VALUE) % tab.length;

         for(UnsyncHTEntry e = tab[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key.equals(key)) {
               return e.value;
            }
         }

         return null;
      }
   }

   protected void rehash() {
      int oldCapacity = this.table.length;
      UnsyncHTEntry[] oldTable = this.table;
      int newCapacity = oldCapacity * 2 + 1;
      UnsyncHTEntry[] newTable = new UnsyncHTEntry[newCapacity];
      this.threshold = (int)((float)newCapacity * this.loadFactor);
      this.table = newTable;
      int i = oldCapacity;

      UnsyncHTEntry e;
      int index;
      while(i-- > 0) {
         for(UnsyncHTEntry old = oldTable[i]; old != null; newTable[index] = e) {
            e = old;
            old = old.next;
            index = (e.hash & Integer.MAX_VALUE) % newCapacity;
            e.next = newTable[index];
         }
      }

   }

   public Object put(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         UnsyncHTEntry[] tab = this.table;
         int hash = key.hashCode();
         int index = (hash & Integer.MAX_VALUE) % tab.length;

         UnsyncHTEntry e;
         for(e = tab[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key.equals(key)) {
               Object old = e.value;
               e.value = value;
               return old;
            }
         }

         if (this.count >= this.threshold) {
            this.rehash();
            return this.put(key, value);
         } else {
            e = new UnsyncHTEntry();
            e.hash = hash;
            e.key = key;
            e.value = value;
            e.next = tab[index];
            tab[index] = e;
            ++this.count;
            return null;
         }
      }
   }

   public Object remove(Object key) {
      UnsyncHTEntry[] tab = this.table;
      int hash = key.hashCode();
      int index = (hash & Integer.MAX_VALUE) % tab.length;
      UnsyncHTEntry e = tab[index];

      for(UnsyncHTEntry prev = null; e != null; e = e.next) {
         if (e.hash == hash && e.key.equals(key)) {
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

   public void clear() {
      UnsyncHTEntry[] tab = this.table;
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

   public Object clone() {
      try {
         UnsyncHashtable t = (UnsyncHashtable)super.clone();
         t.table = new UnsyncHTEntry[this.table.length];

         for(int i = this.table.length; i-- > 0; t.table[i] = this.table[i] != null ? (UnsyncHTEntry)this.table[i].clone() : null) {
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

   public void writeExternal(ObjectOutput s) throws IOException {
      s.writeInt(this.threshold);
      s.writeFloat(this.loadFactor);
      s.writeInt(this.table.length);
      s.writeInt(this.count);

      for(int i = 0; i < this.table.length; ++i) {
         for(UnsyncHTEntry entry = this.table[i]; entry != null; entry = entry.next) {
            s.writeObject(entry.key);
            s.writeObject(entry.value);
         }
      }

   }

   public void readExternal(ObjectInput s) throws IOException, ClassNotFoundException {
      this.threshold = s.readInt();
      this.loadFactor = s.readFloat();
      int origlength = s.readInt();
      int elements = s.readInt();
      int length = (int)((float)elements * this.loadFactor) + elements / 20 + 3;
      if (length > elements && (length & 1) == 0) {
         --length;
      }

      if (origlength > 0 && length > origlength) {
         length = origlength;
      }

      this.table = new UnsyncHTEntry[length];

      for(this.count = 0; elements > 0; --elements) {
         Object key = s.readObject();
         Object value = s.readObject();
         this.put(key, value);
      }

   }

   private static final class UnsyncHTEnumerator implements Enumeration {
      boolean keys;
      int index;
      UnsyncHTEntry[] table;
      UnsyncHTEntry entry;

      UnsyncHTEnumerator(UnsyncHTEntry[] table, boolean keys) {
         this.table = table;
         this.keys = keys;
         this.index = table.length;
      }

      public boolean hasMoreElements() {
         if (this.entry != null) {
            return true;
         } else {
            do {
               if (this.index-- <= 0) {
                  return false;
               }
            } while((this.entry = this.table[this.index]) == null);

            return true;
         }
      }

      public Object nextElement() {
         if (this.entry == null) {
            while(this.index-- > 0 && (this.entry = this.table[this.index]) == null) {
            }
         }

         if (this.entry != null) {
            UnsyncHTEntry e = this.entry;
            this.entry = e.next;
            return this.keys ? e.key : e.value;
         } else {
            throw new NoSuchElementException("UnsyncHTEnumerator");
         }
      }
   }

   private static final class UnsyncHTEntry {
      int hash;
      Object key;
      Object value;
      UnsyncHTEntry next;

      private UnsyncHTEntry() {
      }

      protected Object clone() {
         UnsyncHTEntry entry = new UnsyncHTEntry();
         entry.hash = this.hash;
         entry.key = this.key;
         entry.value = this.value;
         entry.next = this.next != null ? (UnsyncHTEntry)this.next.clone() : null;
         return entry;
      }

      // $FF: synthetic method
      UnsyncHTEntry(Object x0) {
         this();
      }
   }
}
