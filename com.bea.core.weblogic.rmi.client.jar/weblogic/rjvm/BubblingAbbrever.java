package weblogic.rjvm;

import java.util.HashMap;
import java.util.Set;
import weblogic.utils.NestedRuntimeException;

final class BubblingAbbrever {
   private final HashMap keyToEntry;
   private final BubblingAbbreverEntry[] table;
   private int first;

   BubblingAbbrever(int capacity) {
      this.keyToEntry = new HashMap(capacity);
      this.table = new BubblingAbbreverEntry[capacity];
      this.first = capacity;
   }

   BubblingAbbrever() {
      this(32);
   }

   private BubblingAbbreverEntry getEntry(Object key) {
      synchronized(this.keyToEntry) {
         return (BubblingAbbreverEntry)this.keyToEntry.get(key);
      }
   }

   private void bubble(int i) {
      if (i != this.first) {
         int previous = i == 0 ? this.table.length - 1 : i - 1;
         BubblingAbbreverEntry tmp = this.table[previous];
         this.table[previous] = this.table[i];
         this.table[previous].index = previous;
         this.table[i] = tmp;
         tmp.index = i;
      }
   }

   int getCapacity() {
      return this.table.length;
   }

   int getAbbrev(Object value) {
      if (value == null) {
         return this.getCapacity();
      } else {
         BubblingAbbreverEntry e = this.getEntry(value);
         if (e != null) {
            int res = e.index;
            this.bubble(res);
            return res;
         } else {
            this.first = this.first == 0 ? this.table.length - 1 : this.first - 1;
            e = this.table[this.first];
            synchronized(this.keyToEntry) {
               if (e != null) {
                  this.keyToEntry.remove(e.value);
               }

               e = new BubblingAbbreverEntry(value, this.first);
               this.keyToEntry.put(value, e);
            }

            this.table[this.first] = e;
            return this.getCapacity() + 1;
         }
      }
   }

   Object getValue(int i) {
      if (i == this.getCapacity()) {
         return null;
      } else {
         try {
            Object res = this.table[i].value;
            this.bubble(i);
            return res;
         } catch (NullPointerException var3) {
            throw new BadAbbreviationException(i, var3);
         }
      }
   }

   Set getKeySet() {
      synchronized(this.keyToEntry) {
         return this.keyToEntry.keySet();
      }
   }

   public String dump() {
      StringBuffer buffer = new StringBuffer();

      for(int i = this.first; i < this.table.length; ++i) {
         buffer.append("Index=" + this.table[i].index + ", value=" + this.table[i].value + "\n");
      }

      return buffer.toString();
   }

   static final class BadAbbreviationException extends NestedRuntimeException {
      private static final long serialVersionUID = 4762785720909932401L;
      private final int abbrev;

      BadAbbreviationException(int a, Throwable t) {
         super(t);
         this.abbrev = a;
      }

      public String getMessage() {
         return "Bad abbreviation value: '" + this.abbrev + "'";
      }
   }

   static final class BubblingAbbreverEntry {
      final Object value;
      int index;

      BubblingAbbreverEntry(Object value, int index) {
         this.value = value;
         this.index = index;
      }
   }
}
