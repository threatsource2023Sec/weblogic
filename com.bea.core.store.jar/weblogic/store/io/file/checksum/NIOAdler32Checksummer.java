package weblogic.store.io.file.checksum;

import java.nio.ByteBuffer;

public class NIOAdler32Checksummer implements Checksummer {
   private static final String ADLER32_LIB = "ADLER32_LIB";
   private byte[] ONE_BYTE = new byte[1];
   private NIOAdler32 nioAlder32 = new NIOAdler32();

   public void update(int b) {
      this.ONE_BYTE[0] = (byte)b;
      this.update((byte[])this.ONE_BYTE, 0, 1);
   }

   public void update(byte[] b, int off, int len) {
      this.nioAlder32.update(b, off, len);
   }

   public long getValue() {
      return this.nioAlder32.getValue();
   }

   public void reset() {
      this.nioAlder32.reset();
   }

   public void update(ByteBuffer buffer, int offset, int len) {
      if (offset >= 0 && len >= 0 && len + offset <= buffer.capacity()) {
         this.nioAlder32.update(buffer, offset, len);
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public long calculateChecksum(ByteBuffer buffer) {
      this.reset();
      this.update(buffer, buffer.position(), buffer.remaining());
      return this.getValue();
   }

   static {
      System.loadLibrary(System.getProperty("ADLER32_LIB", "adler32"));
   }
}
