package weblogic.io.common.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.io.common.T3FileInputStream;

public final class T3FileInputStreamLocal extends T3FileInputStream {
   private FileInputStream is;

   public T3FileInputStreamLocal(T3FileLocal file) throws T3Exception {
      try {
         this.is = new FileInputStream(file.getPath());
      } catch (FileNotFoundException var3) {
         throw new T3Exception(var3.toString(), var3);
      }
   }

   public int bufferSize() {
      return 0;
   }

   public int readAhead() {
      return 0;
   }

   public int read(byte[] target, int targetOffset, int targetRequestedBytes) throws IOException {
      return this.is.read(target, targetOffset, targetRequestedBytes);
   }

   public int read() throws IOException {
      return this.is.read();
   }

   public long skip(long requestedBytes) throws IOException {
      return this.is.skip(requestedBytes);
   }

   public int available() throws IOException {
      return this.is.available();
   }

   public void close() throws IOException {
      this.is.close();
   }
}
