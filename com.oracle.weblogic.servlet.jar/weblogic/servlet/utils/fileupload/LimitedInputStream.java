package weblogic.servlet.utils.fileupload;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract class LimitedInputStream extends FilterInputStream {
   private long sizeMax;
   private long count;
   private boolean closed;

   public LimitedInputStream(InputStream pIn, long pSizeMax) {
      super(pIn);
      this.sizeMax = pSizeMax;
   }

   protected abstract void raiseError(long var1, long var3) throws SizeException;

   private void checkLimit() throws SizeException {
      if (this.count > this.sizeMax) {
         this.raiseError(this.sizeMax, this.count);
      }

   }

   public int read() throws IOException {
      int res = super.read();
      if (res != -1) {
         ++this.count;
         this.checkLimit();
      }

      return res;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int res = super.read(b, off, len);
      if (res > 0) {
         this.count += (long)res;
         this.checkLimit();
      }

      return res;
   }

   public boolean isClosed() throws IOException {
      return this.closed;
   }

   public void close() throws IOException {
      this.closed = true;
      super.close();
   }
}
