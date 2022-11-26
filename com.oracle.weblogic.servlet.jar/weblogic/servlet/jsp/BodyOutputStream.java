package weblogic.servlet.jsp;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import weblogic.servlet.internal.ChunkOutputWrapper;

class BodyOutputStream extends ServletOutputStream {
   private ChunkOutputWrapper cow;

   BodyOutputStream(ChunkOutputWrapper cow) {
      this.cow = cow;
   }

   public void write(int i) throws IOException {
      this.cow.write(i);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.cow.write(b, off, len);
   }

   public void close() {
   }

   public boolean isReady() {
      return true;
   }

   public void setWriteListener(WriteListener writeListener) {
      throw new IllegalStateException("Not Supported");
   }
}
