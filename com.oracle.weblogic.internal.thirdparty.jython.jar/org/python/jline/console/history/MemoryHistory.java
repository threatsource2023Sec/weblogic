package org.python.jline.console.history;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.python.jline.internal.Preconditions;

public class MemoryHistory implements History {
   public static final int DEFAULT_MAX_SIZE = 500;
   private final LinkedList items = new LinkedList();
   private int maxSize = 500;
   private boolean ignoreDuplicates = true;
   private boolean autoTrim = false;
   private int offset = 0;
   private int index = 0;

   public void setMaxSize(int maxSize) {
      this.maxSize = maxSize;
      this.maybeResize();
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public boolean isIgnoreDuplicates() {
      return this.ignoreDuplicates;
   }

   public void setIgnoreDuplicates(boolean flag) {
      this.ignoreDuplicates = flag;
   }

   public boolean isAutoTrim() {
      return this.autoTrim;
   }

   public void setAutoTrim(boolean flag) {
      this.autoTrim = flag;
   }

   public int size() {
      return this.items.size();
   }

   public boolean isEmpty() {
      return this.items.isEmpty();
   }

   public int index() {
      return this.offset + this.index;
   }

   public void clear() {
      this.items.clear();
      this.offset = 0;
      this.index = 0;
   }

   public CharSequence get(int index) {
      return (CharSequence)this.items.get(index - this.offset);
   }

   public void set(int index, CharSequence item) {
      this.items.set(index - this.offset, item);
   }

   public void add(CharSequence item) {
      Preconditions.checkNotNull(item);
      if (this.isAutoTrim()) {
         item = String.valueOf(item).trim();
      }

      if (!this.isIgnoreDuplicates() || this.items.isEmpty() || !item.equals(this.items.getLast())) {
         this.internalAdd((CharSequence)item);
      }
   }

   public CharSequence remove(int i) {
      return (CharSequence)this.items.remove(i);
   }

   public CharSequence removeFirst() {
      return (CharSequence)this.items.removeFirst();
   }

   public CharSequence removeLast() {
      return (CharSequence)this.items.removeLast();
   }

   protected void internalAdd(CharSequence item) {
      this.items.add(item);
      this.maybeResize();
   }

   public void replace(CharSequence item) {
      this.items.removeLast();
      this.add(item);
   }

   private void maybeResize() {
      while(this.size() > this.getMaxSize()) {
         this.items.removeFirst();
         ++this.offset;
      }

      this.index = this.size();
   }

   public ListIterator entries(int index) {
      return new EntriesIterator(index - this.offset);
   }

   public ListIterator entries() {
      return this.entries(this.offset);
   }

   public Iterator iterator() {
      return this.entries();
   }

   public boolean moveToLast() {
      int lastEntry = this.size() - 1;
      if (lastEntry >= 0 && lastEntry != this.index) {
         this.index = this.size() - 1;
         return true;
      } else {
         return false;
      }
   }

   public boolean moveTo(int index) {
      index -= this.offset;
      if (index >= 0 && index < this.size()) {
         this.index = index;
         return true;
      } else {
         return false;
      }
   }

   public boolean moveToFirst() {
      if (this.size() > 0 && this.index != 0) {
         this.index = 0;
         return true;
      } else {
         return false;
      }
   }

   public void moveToEnd() {
      this.index = this.size();
   }

   public CharSequence current() {
      return (CharSequence)(this.index >= this.size() ? "" : (CharSequence)this.items.get(this.index));
   }

   public boolean previous() {
      if (this.index <= 0) {
         return false;
      } else {
         --this.index;
         return true;
      }
   }

   public boolean next() {
      if (this.index >= this.size()) {
         return false;
      } else {
         ++this.index;
         return true;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         History.Entry e = (History.Entry)var2.next();
         sb.append(e.toString() + "\n");
      }

      return sb.toString();
   }

   private class EntriesIterator implements ListIterator {
      private final ListIterator source;

      private EntriesIterator(int index) {
         this.source = MemoryHistory.this.items.listIterator(index);
      }

      public History.Entry next() {
         if (!this.source.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return new EntryImpl(MemoryHistory.this.offset + this.source.nextIndex(), (CharSequence)this.source.next());
         }
      }

      public History.Entry previous() {
         if (!this.source.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return new EntryImpl(MemoryHistory.this.offset + this.source.previousIndex(), (CharSequence)this.source.previous());
         }
      }

      public int nextIndex() {
         return MemoryHistory.this.offset + this.source.nextIndex();
      }

      public int previousIndex() {
         return MemoryHistory.this.offset + this.source.previousIndex();
      }

      public boolean hasNext() {
         return this.source.hasNext();
      }

      public boolean hasPrevious() {
         return this.source.hasPrevious();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(History.Entry entry) {
         throw new UnsupportedOperationException();
      }

      public void add(History.Entry entry) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      EntriesIterator(int x1, Object x2) {
         this(x1);
      }
   }

   private static class EntryImpl implements History.Entry {
      private final int index;
      private final CharSequence value;

      public EntryImpl(int index, CharSequence value) {
         this.index = index;
         this.value = value;
      }

      public int index() {
         return this.index;
      }

      public CharSequence value() {
         return this.value;
      }

      public String toString() {
         return String.format("%d: %s", this.index, this.value);
      }
   }
}
