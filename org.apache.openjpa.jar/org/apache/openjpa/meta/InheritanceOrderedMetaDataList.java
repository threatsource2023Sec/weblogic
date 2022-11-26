package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class InheritanceOrderedMetaDataList implements Serializable {
   private MetaDataInheritanceComparator _comp = new MetaDataInheritanceComparator();
   private LinkedList buffer = new LinkedList();

   public boolean add(ClassMetaData meta) {
      if (meta != null && !this.buffer.contains(meta)) {
         ListIterator itr = this.buffer.listIterator();

         int ord;
         do {
            if (!itr.hasNext()) {
               this.buffer.add(meta);
               return true;
            }

            ord = this._comp.compare(meta, itr.next());
         } while(ord > 0);

         if (ord == 0) {
            return false;
         } else {
            itr.previous();
            itr.add(meta);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean remove(ClassMetaData meta) {
      return this.buffer.remove(meta);
   }

   public ClassMetaData peek() {
      return (ClassMetaData)this.buffer.peek();
   }

   public int size() {
      return this.buffer.size();
   }

   public Iterator iterator() {
      return this.buffer.iterator();
   }

   public boolean isEmpty() {
      return this.buffer.isEmpty();
   }

   public void clear() {
      this.buffer.clear();
   }
}
