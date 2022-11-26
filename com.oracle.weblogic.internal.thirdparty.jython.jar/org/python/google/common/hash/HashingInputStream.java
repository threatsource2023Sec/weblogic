package org.python.google.common.hash;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public final class HashingInputStream extends FilterInputStream {
   private final Hasher hasher;

   public HashingInputStream(HashFunction hashFunction, InputStream in) {
      super((InputStream)Preconditions.checkNotNull(in));
      this.hasher = (Hasher)Preconditions.checkNotNull(hashFunction.newHasher());
   }

   @CanIgnoreReturnValue
   public int read() throws IOException {
      int b = this.in.read();
      if (b != -1) {
         this.hasher.putByte((byte)b);
      }

      return b;
   }

   @CanIgnoreReturnValue
   public int read(byte[] bytes, int off, int len) throws IOException {
      int numOfBytesRead = this.in.read(bytes, off, len);
      if (numOfBytesRead != -1) {
         this.hasher.putBytes(bytes, off, numOfBytesRead);
      }

      return numOfBytesRead;
   }

   public boolean markSupported() {
      return false;
   }

   public void mark(int readlimit) {
   }

   public void reset() throws IOException {
      throw new IOException("reset not supported");
   }

   public HashCode hash() {
      return this.hasher.hash();
   }
}
