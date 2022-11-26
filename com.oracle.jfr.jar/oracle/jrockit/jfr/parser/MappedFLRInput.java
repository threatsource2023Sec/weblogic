package oracle.jrockit.jfr.parser;

import java.io.IOException;
import java.nio.MappedByteBuffer;

class MappedFLRInput implements FLRInput {
   private final MappedByteBuffer buffer;

   public MappedFLRInput(MappedByteBuffer buffer) {
      this.buffer = buffer;
   }

   public byte get() {
      return this.buffer.get();
   }

   public void get(byte[] dst, int offset, int length) {
      this.buffer.get(dst, offset, length);
   }

   public void get(byte[] dst) {
      this.buffer.get(dst);
   }

   public char getChar() {
      return this.buffer.getChar();
   }

   public double getDouble() {
      return this.buffer.getDouble();
   }

   public float getFloat() {
      return this.buffer.getFloat();
   }

   public int getInt() {
      return this.buffer.getInt();
   }

   public long getLong() {
      return this.buffer.getLong();
   }

   public short getShort() {
      return this.buffer.getShort();
   }

   public final long position() {
      return (long)this.buffer.position();
   }

   public final void position(long newPosition) {
      this.buffer.position((int)newPosition);
   }

   public long size() {
      return (long)this.buffer.limit();
   }

   public void close() throws IOException {
   }
}
