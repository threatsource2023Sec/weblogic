package weblogic.nodemanager.common;

import java.io.IOException;

public class Chunk {
   public static final int DEFAULT_SIZE = 4096;
   public static final int SIZE_BYTES = 2;
   private final byte[] buf;
   private int pos;

   public Chunk() {
      this(4096);
   }

   public Chunk(int length) {
      this.pos = 2;
      if (!this.checkLength(length, 1, 65535)) {
         throw new IllegalArgumentException();
      } else {
         this.buf = new byte[length + 2];
      }
   }

   public int getLength() throws IOException {
      int length = ((this.buf[0] & 255) << 8) + (this.buf[1] & 255);
      if (!this.checkLength(length, 0, this.buf.length - 2)) {
         throw new InvalidChunkLengthException("Invalid length: " + length + ", the size of Chunk: " + (this.buf.length - 2));
      } else {
         return length;
      }
   }

   public void setLength(int length) throws IOException {
      if (!this.checkLength(length, 1, this.buf.length - 2)) {
         throw new InvalidChunkLengthException("Invalid length: " + length + ", the size of Chunk: " + (this.buf.length - 2));
      } else {
         this.setLengthInternal(length);
      }
   }

   public void setLengthInternal(int length) {
      this.buf[0] = (byte)(length >>> 8);
      this.buf[1] = (byte)length;
   }

   private boolean checkLength(int length, int minLength, int maxLength) {
      return length >= minLength && length <= maxLength;
   }

   boolean atEnd() {
      return this.pos == this.buf.length;
   }

   void write(int val) {
      this.buf[this.pos++] = (byte)val;
   }

   public void close() {
      this.setLengthInternal(this.pos - 2);
   }

   public void reset() {
      this.pos = 2;
   }

   public byte[] getBytes() {
      return this.buf;
   }

   public int getPos() {
      return this.pos;
   }

   public void advancePos(int read) throws IOException {
      if (read < -1) {
         throw new IOException("Unknown exception of the reading. Reading result:" + read);
      } else if (read == -1) {
         throw new InvalidChunkLengthException("Invalid length: " + (this.buf.length - 2) + ", actually only " + (this.pos + 1 - 2) + " (besides the size bytes) can be read.");
      } else if (this.pos + read > this.buf.length) {
         throw new InvalidChunkLengthException("Invalid length: " + (this.buf.length - 2) + ", but more to be read.");
      } else {
         this.pos += read;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getClass().getName());
      sb.append("[pos=");
      sb.append(this.pos);
      sb.append(" len=");
      sb.append(((this.buf[0] & 255) << 8) + (this.buf[1] & 255));
      sb.append(" cap=");
      sb.append(this.buf.length);
      sb.append("]");
      return sb.toString();
   }

   public static class InvalidChunkLengthException extends IOException {
      public InvalidChunkLengthException(String msg) {
         super(msg);
      }
   }
}
