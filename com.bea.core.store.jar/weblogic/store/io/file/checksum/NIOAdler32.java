package weblogic.store.io.file.checksum;

import java.nio.ByteBuffer;
import java.util.zip.Checksum;

class NIOAdler32 implements Checksum {
   private int adler = 1;

   public void update(int b) {
      this.adler = update(this.adler, b);
   }

   public void update(byte[] b, int off, int len) {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && len >= 0 && off <= b.length - len) {
         this.adler = updateBytes(this.adler, b, off, len);
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public void update(byte[] b) {
      this.adler = updateBytes(this.adler, b, 0, b.length);
   }

   public void update(ByteBuffer b) {
      this.update(b, b.position(), b.remaining());
   }

   public void update(ByteBuffer b, int off, int len) {
      if (b.hasArray()) {
         this.update(b.array(), b.arrayOffset() + off, len);
      } else {
         this.adler = updateByteBuffer(this.adler, b, off, len);
      }

   }

   public void reset() {
      this.adler = 1;
   }

   public long getValue() {
      return (long)this.adler & 4294967295L;
   }

   private static native int update(int var0, int var1);

   private static native int updateBytes(int var0, byte[] var1, int var2, int var3);

   private static native int updateByteBuffer(int var0, ByteBuffer var1, int var2, int var3);

   static {
      System.loadLibrary(System.getProperty("ADLER32_LIB", "adler32"));
   }
}
