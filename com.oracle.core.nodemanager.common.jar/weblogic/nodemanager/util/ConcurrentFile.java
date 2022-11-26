package weblogic.nodemanager.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class ConcurrentFile extends File {
   private static final String EOL = System.getProperty("line.separator");

   protected ConcurrentFile(String path) {
      super(path);
   }

   public abstract void write(ByteBuffer var1) throws IOException;

   public abstract int read(ByteBuffer var1) throws FileNotFoundException, IOException;

   public String readLine() throws IOException {
      byte[] b = new byte[512];
      int len = this.read(ByteBuffer.wrap(b));
      if (len == -1) {
         return null;
      } else {
         String line = new String(b, 0, len);
         int i = line.indexOf(10);
         if (i > 0 && line.charAt(i - 1) == '\r') {
            --i;
         }

         if (i >= 0) {
            line = line.substring(0, i);
         }

         return line;
      }
   }

   public void writeLine(String line) throws IOException {
      this.write(ByteBuffer.wrap((line + EOL).getBytes()));
   }
}
