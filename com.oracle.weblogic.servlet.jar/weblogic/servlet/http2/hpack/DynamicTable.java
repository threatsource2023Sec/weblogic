package weblogic.servlet.http2.hpack;

import java.util.LinkedList;

public final class DynamicTable {
   private HeaderEntryQueue table = null;

   protected DynamicTable(int maxTableSize) {
      this.table = new HeaderEntryQueue(maxTableSize);
   }

   protected int getIndex(HeaderEntry entry) {
      return this.table.size() == 0 ? -1 : this.table.indexOf(entry);
   }

   protected int length() {
      return this.table.size();
   }

   protected int getNameIndex(String name) {
      if (this.table.size() == 0) {
         return -1;
      } else {
         HeaderEntry entry = new HeaderEntry(name, HeaderEntry.NULL_VALUE);
         entry.setCheckNameOnly(true);
         return this.table.indexOf(entry);
      }
   }

   public HeaderEntry getEntry(int index) {
      return (HeaderEntry)this.table.get(this.table.size() - index - 1);
   }

   public void addEntry(HeaderEntry entry) {
      int size = entry.size();
      if (this.table.exceedMaxTableSize(size)) {
         this.table.clear();
      } else {
         while(!this.table.hasSpaceToAdd(entry.size())) {
            this.table.remove();
         }

         this.table.add(entry);
      }
   }

   public Object[] getDynamicTableAsArray() {
      return this.table.toArray();
   }

   public int tableSize() {
      return this.table.getCurrentTableSize();
   }

   public int getMaxTableSize() {
      return this.table.getMaxTableSize();
   }

   public void setMaxTableSize(int maxTableSize) {
      this.table.setMaxTableSize(maxTableSize);
      if (maxTableSize == 0) {
         this.table.clear();
      } else {
         while(!this.table.hasSpaceToAdd(0)) {
            this.table.remove();
         }

      }
   }

   private final class HeaderEntryQueue extends LinkedList {
      private int usedTableSize = 0;
      private int maxTableSize = 0;

      protected HeaderEntryQueue(int tableLimit) {
         this.maxTableSize = tableLimit;
      }

      public boolean add(Object e) {
         if (e instanceof HeaderEntry) {
            this.increaseUsedTableSize(((HeaderEntry)e).size());
         }

         super.add(e);
         return true;
      }

      public void clear() {
         this.usedTableSize = 0;
         super.clear();
      }

      public Object remove() {
         Object e = this.removeFirst();
         if (e instanceof HeaderEntry) {
            this.decreaseUsedTableSize(((HeaderEntry)e).size());
         }

         return e;
      }

      public int indexOf(Object o) {
         int index = super.lastIndexOf(o);
         return index >= 0 ? super.size() - index : -1;
      }

      private void increaseUsedTableSize(int size) {
         this.usedTableSize += size;
      }

      private void decreaseUsedTableSize(int size) {
         this.usedTableSize -= size;
      }

      protected int getCurrentTableSize() {
         return this.usedTableSize;
      }

      protected boolean hasSpaceToAdd(int size) {
         return size < this.availableSpace();
      }

      private int availableSpace() {
         return this.maxTableSize - this.getCurrentTableSize();
      }

      private boolean exceedMaxTableSize(int size) {
         return size > this.maxTableSize;
      }

      public int getMaxTableSize() {
         return this.maxTableSize;
      }

      public void setMaxTableSize(int maxTableSize) {
         this.maxTableSize = maxTableSize;
      }
   }
}
