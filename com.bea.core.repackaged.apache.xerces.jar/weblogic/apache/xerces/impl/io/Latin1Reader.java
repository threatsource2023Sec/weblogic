package weblogic.apache.xerces.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public final class Latin1Reader extends Reader {
   public static final int DEFAULT_BUFFER_SIZE = 2048;
   protected final InputStream fInputStream;
   protected final byte[] fBuffer;

   public Latin1Reader(InputStream var1) {
      this(var1, 2048);
   }

   public Latin1Reader(InputStream var1, int var2) {
      this(var1, new byte[var2]);
   }

   public Latin1Reader(InputStream var1, byte[] var2) {
      this.fInputStream = var1;
      this.fBuffer = var2;
   }

   public int read() throws IOException {
      return this.fInputStream.read();
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (var3 > this.fBuffer.length) {
         var3 = this.fBuffer.length;
      }

      int var4 = this.fInputStream.read(this.fBuffer, 0, var3);

      for(int var5 = 0; var5 < var4; ++var5) {
         var1[var2 + var5] = (char)(this.fBuffer[var5] & 255);
      }

      return var4;
   }

   public long skip(long var1) throws IOException {
      return this.fInputStream.skip(var1);
   }

   public boolean ready() throws IOException {
      return false;
   }

   public boolean markSupported() {
      return this.fInputStream.markSupported();
   }

   public void mark(int var1) throws IOException {
      this.fInputStream.mark(var1);
   }

   public void reset() throws IOException {
      this.fInputStream.reset();
   }

   public void close() throws IOException {
      this.fInputStream.close();
   }
}
