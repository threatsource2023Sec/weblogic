package javolution.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javolution.realtime.RealtimeObject;

final class FastIterator extends RealtimeObject implements Iterator {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      protected Object create() {
         return new FastIterator();
      }

      protected void cleanup(Object var1) {
         FastIterator var2 = (FastIterator)var1;
         FastIterator.access$102(var2, (FastCollection)null);
         FastIterator.access$202(var2, (FastCollection.Record)null);
         FastIterator.access$302(var2, (FastCollection.Record)null);
         FastIterator.access$402(var2, (FastCollection.Record)null);
      }
   };
   private FastCollection _collection;
   private FastCollection.Record _current;
   private FastCollection.Record _next;
   private FastCollection.Record _tail;

   public static FastIterator valueOf(FastCollection var0) {
      FastIterator var1 = (FastIterator)FACTORY.object();
      var1._collection = var0;
      var1._next = var0.head().getNext();
      var1._tail = var0.tail();
      return var1;
   }

   private FastIterator() {
   }

   public boolean hasNext() {
      return this._next != this._tail;
   }

   public Object next() {
      if (this._next == this._tail) {
         throw new NoSuchElementException();
      } else {
         this._current = this._next;
         this._next = this._next.getNext();
         return this._collection.valueOf(this._current);
      }
   }

   public void remove() {
      if (this._current != null) {
         FastCollection.Record var1 = this._current.getPrevious();
         this._collection.delete(this._current);
         this._current = null;
         this._next = var1.getNext();
      } else {
         throw new IllegalStateException();
      }
   }

   FastIterator(Object var1) {
      this();
   }

   static FastCollection access$102(FastIterator var0, FastCollection var1) {
      return var0._collection = var1;
   }

   static FastCollection.Record access$202(FastIterator var0, FastCollection.Record var1) {
      return var0._current = var1;
   }

   static FastCollection.Record access$302(FastIterator var0, FastCollection.Record var1) {
      return var0._next = var1;
   }

   static FastCollection.Record access$402(FastIterator var0, FastCollection.Record var1) {
      return var0._tail = var1;
   }
}
