package weblogic.deploy.api.internal.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpenInputStream extends BufferedInputStream {
   public OpenInputStream(InputStream is) {
      super(is);
   }

   public OpenInputStream(InputStream is, int size) {
      super(is, size);
   }

   public void close() throws IOException {
      super.reset();
   }

   public void closeOut() throws IOException {
      super.close();
   }
}
