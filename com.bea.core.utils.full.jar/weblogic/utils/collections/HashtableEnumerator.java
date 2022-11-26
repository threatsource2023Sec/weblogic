package weblogic.utils.collections;

import java.util.Enumeration;
import java.util.NoSuchElementException;

class HashtableEnumerator implements Enumeration {
   boolean keys;
   int index;
   HashtableEntry[] table;
   HashtableEntry entry;

   HashtableEnumerator(HashtableEntry[] table, boolean keys) {
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
         HashtableEntry e = this.entry;
         this.entry = e.next;
         return this.keys ? new Long(e.key) : e.value;
      } else {
         throw new NoSuchElementException("HashtableEnumerator");
      }
   }
}
