package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import org.apache.commons.collections.map.AbstractLinkedMap;

public class LRUMap extends org.apache.commons.collections.map.LRUMap implements SizedMap {
   private int _max = -1;

   public LRUMap() {
   }

   public LRUMap(int initCapacity) {
      super(initCapacity);
   }

   public LRUMap(int initCapacity, float loadFactor) {
      super(initCapacity, loadFactor);
   }

   public LRUMap(Map map) {
      super(map);
   }

   public int getMaxSize() {
      return this.maxSize();
   }

   public void setMaxSize(int max) {
      if (max < 0) {
         throw new IllegalArgumentException(String.valueOf(max));
      } else {
         this._max = max;

         while(this.size() > this._max) {
            Object key = this.lastKey();
            this.overflowRemoved(key, this.remove(key));
         }

      }
   }

   public void overflowRemoved(Object key, Object value) {
   }

   public int maxSize() {
      return this._max == -1 ? super.maxSize() : this._max;
   }

   public boolean isFull() {
      return this._max == -1 ? super.isFull() : this.size() >= this._max;
   }

   protected boolean removeLRU(AbstractLinkedMap.LinkEntry entry) {
      this.overflowRemoved(entry.getKey(), entry.getValue());
      return super.removeLRU(entry);
   }

   protected void doWriteObject(ObjectOutputStream out) throws IOException {
      out.writeInt(this._max);
      super.doWriteObject(out);
   }

   protected void doReadObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._max = in.readInt();
      super.doReadObject(in);
   }
}
