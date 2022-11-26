package org.apache.openjpa.lib.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import org.apache.openjpa.lib.util.SizedMap;

public class SizedConcurrentHashMap extends NullSafeConcurrentHashMap implements SizedMap, ConcurrentMap, Serializable {
   private int maxSize;

   public SizedConcurrentHashMap(int size, float load, int concurrencyLevel) {
      super(size, load, concurrencyLevel);
      this.setMaxSize(size);
   }

   public Object putIfAbsent(Object key, Object value) {
      if (this.maxSize != Integer.MAX_VALUE) {
         this.removeOverflow(true);
      }

      return super.putIfAbsent(key, value);
   }

   public Object put(Object key, Object value) {
      if (this.maxSize != Integer.MAX_VALUE) {
         this.removeOverflow(true);
      }

      return super.put(key, value);
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public void setMaxSize(int max) {
      if (max < 0) {
         throw new IllegalArgumentException(String.valueOf(max));
      } else {
         this.maxSize = max;
         this.removeOverflow(false);
      }
   }

   protected void removeOverflow() {
      this.removeOverflow(false);
   }

   protected void removeOverflow(boolean forPut) {
      int sizeToCompareTo = forPut ? this.maxSize - 1 : this.maxSize;

      while(this.size() > sizeToCompareTo) {
         Map.Entry entry = this.removeRandom();
         if (entry == null) {
            break;
         }

         this.overflowRemoved(entry.getKey(), entry.getValue());
      }

   }

   public boolean isFull() {
      return this.size() >= this.maxSize;
   }

   public void overflowRemoved(Object key, Object value) {
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      out.writeInt(this.maxSize);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.maxSize = in.readInt();
   }
}
