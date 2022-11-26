package weblogic.io.common.internal;

import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.io.common.T3FileInputStream;

public final class T3FileInputStreamRemote extends T3FileInputStream {
   private static int count = 0;
   private int bufferSize;
   private int readAhead;
   private T3RemoteInputStream ris;

   public T3FileInputStreamRemote(T3FileSystemProxy rfs, T3FileRemote file, int bufferSize, int readAhead) throws T3Exception {
      this.bufferSize = bufferSize;
      this.readAhead = readAhead;
      ++count;
      this.ris = new T3RemoteInputStream(bufferSize, readAhead);
      this.ris.setOneWayRemote(rfs.createInputStream(this.ris, file.getPath(), bufferSize, readAhead));
   }

   public int bufferSize() {
      return this.bufferSize;
   }

   public int readAhead() {
      return this.readAhead;
   }

   public int read(byte[] target, int targetOffset, int targetRequestedBytes) throws IOException {
      return this.ris.read(target, targetOffset, targetRequestedBytes);
   }

   public int read() throws IOException {
      return this.ris.read();
   }

   public long skip(long requestedBytes) throws IOException {
      return this.ris.skip(requestedBytes);
   }

   public int available() throws IOException {
      return this.ris.available();
   }

   public void close() throws IOException {
      this.ris.close();
   }
}
