package org.python.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

final class SelectedSelectionKeySet extends AbstractSet {
   SelectionKey[] keys = new SelectionKey[1024];
   int size;

   public boolean add(SelectionKey o) {
      if (o == null) {
         return false;
      } else {
         this.keys[this.size++] = o;
         if (this.size == this.keys.length) {
            this.increaseCapacity();
         }

         return true;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean remove(Object o) {
      return false;
   }

   public boolean contains(Object o) {
      return false;
   }

   public Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   void reset() {
      this.reset(0);
   }

   void reset(int start) {
      Arrays.fill(this.keys, start, this.size, (Object)null);
      this.size = 0;
   }

   private void increaseCapacity() {
      SelectionKey[] newKeys = new SelectionKey[this.keys.length << 1];
      System.arraycopy(this.keys, 0, newKeys, 0, this.size);
      this.keys = newKeys;
   }
}
