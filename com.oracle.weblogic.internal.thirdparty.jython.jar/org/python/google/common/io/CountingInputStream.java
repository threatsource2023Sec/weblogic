package org.python.google.common.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@Beta
@GwtIncompatible
public final class CountingInputStream extends FilterInputStream {
   private long count;
   private long mark = -1L;

   public CountingInputStream(InputStream in) {
      super((InputStream)Preconditions.checkNotNull(in));
   }

   public long getCount() {
      return this.count;
   }

   public int read() throws IOException {
      int result = this.in.read();
      if (result != -1) {
         ++this.count;
      }

      return result;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int result = this.in.read(b, off, len);
      if (result != -1) {
         this.count += (long)result;
      }

      return result;
   }

   public long skip(long n) throws IOException {
      long result = this.in.skip(n);
      this.count += result;
      return result;
   }

   public synchronized void mark(int readlimit) {
      this.in.mark(readlimit);
      this.mark = this.count;
   }

   public synchronized void reset() throws IOException {
      if (!this.in.markSupported()) {
         throw new IOException("Mark not supported");
      } else if (this.mark == -1L) {
         throw new IOException("Mark not set");
      } else {
         this.in.reset();
         this.count = this.mark;
      }
   }
}
