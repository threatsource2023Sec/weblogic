package weblogic.xml.saaj.mime4j.decoder;

import java.util.Iterator;
import java.util.NoSuchElementException;

class UnboundedFifoByteBuffer {
   protected byte[] buffer;
   protected int head;
   protected int tail;

   public UnboundedFifoByteBuffer() {
      this(32);
   }

   public UnboundedFifoByteBuffer(int initialSize) {
      if (initialSize <= 0) {
         throw new IllegalArgumentException("The size must be greater than 0");
      } else {
         this.buffer = new byte[initialSize + 1];
         this.head = 0;
         this.tail = 0;
      }
   }

   public int size() {
      int size = false;
      int size;
      if (this.tail < this.head) {
         size = this.buffer.length - this.head + this.tail;
      } else {
         size = this.tail - this.head;
      }

      return size;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean add(byte b) {
      if (this.size() + 1 >= this.buffer.length) {
         byte[] tmp = new byte[(this.buffer.length - 1) * 2 + 1];
         int j = 0;
         int i = this.head;

         while(i != this.tail) {
            tmp[j] = this.buffer[i];
            this.buffer[i] = 0;
            ++j;
            ++i;
            if (i == this.buffer.length) {
               i = 0;
            }
         }

         this.buffer = tmp;
         this.head = 0;
         this.tail = j;
      }

      this.buffer[this.tail] = b;
      ++this.tail;
      if (this.tail >= this.buffer.length) {
         this.tail = 0;
      }

      return true;
   }

   public byte get() {
      if (this.isEmpty()) {
         throw new IllegalStateException("The buffer is already empty");
      } else {
         return this.buffer[this.head];
      }
   }

   public byte remove() {
      if (this.isEmpty()) {
         throw new IllegalStateException("The buffer is already empty");
      } else {
         byte element = this.buffer[this.head];
         ++this.head;
         if (this.head >= this.buffer.length) {
            this.head = 0;
         }

         return element;
      }
   }

   private int increment(int index) {
      ++index;
      if (index >= this.buffer.length) {
         index = 0;
      }

      return index;
   }

   private int decrement(int index) {
      --index;
      if (index < 0) {
         index = this.buffer.length - 1;
      }

      return index;
   }

   public Iterator iterator() {
      return new Iterator() {
         private int index;
         private int lastReturnedIndex;

         {
            this.index = UnboundedFifoByteBuffer.this.head;
            this.lastReturnedIndex = -1;
         }

         public boolean hasNext() {
            return this.index != UnboundedFifoByteBuffer.this.tail;
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               this.lastReturnedIndex = this.index;
               this.index = UnboundedFifoByteBuffer.this.increment(this.index);
               return new Byte(UnboundedFifoByteBuffer.this.buffer[this.lastReturnedIndex]);
            }
         }

         public void remove() {
            if (this.lastReturnedIndex == -1) {
               throw new IllegalStateException();
            } else if (this.lastReturnedIndex == UnboundedFifoByteBuffer.this.head) {
               UnboundedFifoByteBuffer.this.remove();
               this.lastReturnedIndex = -1;
            } else {
               int i = this.lastReturnedIndex + 1;

               while(i != UnboundedFifoByteBuffer.this.tail) {
                  if (i >= UnboundedFifoByteBuffer.this.buffer.length) {
                     UnboundedFifoByteBuffer.this.buffer[i - 1] = UnboundedFifoByteBuffer.this.buffer[0];
                     i = 0;
                  } else {
                     UnboundedFifoByteBuffer.this.buffer[i - 1] = UnboundedFifoByteBuffer.this.buffer[i];
                     ++i;
                  }
               }

               this.lastReturnedIndex = -1;
               UnboundedFifoByteBuffer.this.tail = UnboundedFifoByteBuffer.this.decrement(UnboundedFifoByteBuffer.this.tail);
               UnboundedFifoByteBuffer.this.buffer[UnboundedFifoByteBuffer.this.tail] = 0;
               this.index = UnboundedFifoByteBuffer.this.decrement(this.index);
            }
         }
      };
   }
}
