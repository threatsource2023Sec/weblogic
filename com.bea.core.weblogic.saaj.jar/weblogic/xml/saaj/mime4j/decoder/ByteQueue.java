package weblogic.xml.saaj.mime4j.decoder;

import java.util.Iterator;

public class ByteQueue {
   private UnboundedFifoByteBuffer buf;
   private int initialCapacity = -1;

   public ByteQueue() {
      this.buf = new UnboundedFifoByteBuffer();
   }

   public ByteQueue(int initialCapacity) {
      this.buf = new UnboundedFifoByteBuffer(initialCapacity);
      this.initialCapacity = initialCapacity;
   }

   public void enqueue(byte b) {
      this.buf.add(b);
   }

   public byte dequeue() {
      return this.buf.remove();
   }

   public int count() {
      return this.buf.size();
   }

   public void clear() {
      if (this.initialCapacity != -1) {
         this.buf = new UnboundedFifoByteBuffer(this.initialCapacity);
      } else {
         this.buf = new UnboundedFifoByteBuffer();
      }

   }

   public Iterator iterator() {
      return this.buf.iterator();
   }
}
