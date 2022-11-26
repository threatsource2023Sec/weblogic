package weblogic.jms.utils.tracing;

import java.nio.ByteBuffer;

public class SubBuffer {
   private ByteBuffer buffer;
   private int offset;
   private int length;

   public SubBuffer(ByteBuffer buffer, int offset, int length) {
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
   }

   public void putInt(int offset, int value) {
      if (offset > this.length) {
         throw new AssertionError("Yup");
      } else {
         this.buffer.putInt(this.offset + offset, value);
      }
   }

   public int getInt(int offset) {
      if (offset > this.length) {
         throw new AssertionError("Yup");
      } else {
         return this.buffer.getInt(this.offset + offset);
      }
   }

   public String toString() {
      if (this.length == 0) {
         return null;
      } else {
         int strlen = this.length / 2;
         char[] tmp = new char[strlen];

         for(int i = 0; i < strlen; ++i) {
            tmp[i] = this.buffer.getChar(this.offset + i * 2);
         }

         return new String(tmp, 0, strlen - 1);
      }
   }

   public int limit() {
      return this.length;
   }
}
