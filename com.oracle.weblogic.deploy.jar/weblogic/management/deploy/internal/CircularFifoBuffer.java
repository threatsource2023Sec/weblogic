package weblogic.management.deploy.internal;

import java.lang.reflect.Array;

public class CircularFifoBuffer {
   private final Object[] circularBuffer;
   private final int size;
   private int length = 0;
   private int head = 0;
   private int tail = -1;

   public CircularFifoBuffer(Class clz, int size) {
      if (size <= 1) {
         throw new IllegalArgumentException("Size must be great than 1 " + size);
      } else {
         this.size = size;
         this.circularBuffer = (Object[])((Object[])Array.newInstance(clz, size));
      }
   }

   public void add(Object o) {
      if (++this.tail == this.size) {
         this.tail = 0;
      }

      this.circularBuffer[this.tail] = o;
      if (this.length < this.size) {
         ++this.length;
      } else if (++this.head == this.size) {
         this.head = 0;
      }

   }

   public Object remove() {
      if (this.length == 0) {
         return null;
      } else {
         Object o = this.circularBuffer[this.head];
         --this.length;
         if (++this.head == this.size) {
            this.head = 0;
         }

         return o;
      }
   }
}
