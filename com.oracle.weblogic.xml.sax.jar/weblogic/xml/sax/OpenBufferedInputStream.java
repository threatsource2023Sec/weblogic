package weblogic.xml.sax;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpenBufferedInputStream extends BufferedInputStream {
   private boolean allowClose = false;

   public OpenBufferedInputStream(InputStream in) {
      super(in);
   }

   public OpenBufferedInputStream(InputStream in, int size) {
      super(in, size);
   }

   public void allowClose(boolean value) {
      this.allowClose = value;
   }

   public void close() throws IOException {
      if (this.allowClose) {
         super.close();
      }

   }
}
