package org.python.icu.impl.coll;

public final class UVector64 {
   private long[] buffer = new long[32];
   private int length = 0;

   public boolean isEmpty() {
      return this.length == 0;
   }

   public int size() {
      return this.length;
   }

   public long elementAti(int i) {
      return this.buffer[i];
   }

   public long[] getBuffer() {
      return this.buffer;
   }

   public void addElement(long e) {
      this.ensureAppendCapacity();
      this.buffer[this.length++] = e;
   }

   public void setElementAt(long elem, int index) {
      this.buffer[index] = elem;
   }

   public void insertElementAt(long elem, int index) {
      this.ensureAppendCapacity();
      System.arraycopy(this.buffer, index, this.buffer, index + 1, this.length - index);
      this.buffer[index] = elem;
      ++this.length;
   }

   public void removeAllElements() {
      this.length = 0;
   }

   private void ensureAppendCapacity() {
      if (this.length >= this.buffer.length) {
         int newCapacity = this.buffer.length <= 65535 ? 4 * this.buffer.length : 2 * this.buffer.length;
         long[] newBuffer = new long[newCapacity];
         System.arraycopy(this.buffer, 0, newBuffer, 0, this.length);
         this.buffer = newBuffer;
      }

   }
}
