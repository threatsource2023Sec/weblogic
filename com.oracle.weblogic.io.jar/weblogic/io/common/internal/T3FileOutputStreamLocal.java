package weblogic.io.common.internal;

import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.io.common.T3FileOutputStream;

public final class T3FileOutputStreamLocal extends T3FileOutputStream {
   private FileOutputStream os;

   public T3FileOutputStreamLocal(T3FileLocal file) throws T3Exception {
      try {
         this.os = new FileOutputStream(file.getPath());
      } catch (IOException var3) {
         throw new T3Exception(var3.toString(), var3);
      }
   }

   public int bufferSize() {
      return 0;
   }

   public int writeBehind() {
      return 0;
   }

   public void write(byte[] source, int sourceOffset, int sourceRequestedBytes) throws IOException {
      this.os.write(source, sourceOffset, sourceRequestedBytes);
   }

   public void write(int b) throws IOException {
      this.os.write(b);
   }

   public void flush() throws IOException {
      this.os.flush();
   }

   public void close() throws IOException {
      this.os.close();
   }
}
