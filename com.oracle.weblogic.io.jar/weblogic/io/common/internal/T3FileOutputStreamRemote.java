package weblogic.io.common.internal;

import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.io.common.T3FileOutputStream;

public final class T3FileOutputStreamRemote extends T3FileOutputStream {
   private static int count = 0;
   private int bufferSize;
   private int writeBehind;
   private T3RemoteOutputStream ros;

   public T3FileOutputStreamRemote(T3FileSystemProxy rfs, T3FileRemote file, int bufferSize, int writeBehind) throws T3Exception {
      this.bufferSize = bufferSize;
      this.writeBehind = writeBehind;
      ++count;
      this.ros = new T3RemoteOutputStream(bufferSize, writeBehind);
      this.ros.setOneWayRemote(rfs.createOutputStream(this.ros, file.getPath(), bufferSize));
   }

   public int bufferSize() {
      return this.bufferSize;
   }

   public int writeBehind() {
      return this.writeBehind;
   }

   public void write(byte[] source, int sourceOffset, int sourceRequestedBytes) throws IOException {
      this.ros.write(source, sourceOffset, sourceRequestedBytes);
   }

   public void write(int b) throws IOException {
      this.ros.write(b);
   }

   public void flush() throws IOException {
      this.ros.flush();
   }

   public void close() throws IOException {
      this.ros.close();
   }
}
