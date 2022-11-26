package weblogic.deploy.utils;

import java.io.BufferedInputStream;
import java.io.File;

class TransferHelper {
   private BufferedInputStream stream;
   private File source;

   public TransferHelper(BufferedInputStream stream, File source) {
      this.stream = stream;
      this.source = source;
   }

   BufferedInputStream getStream() {
      return this.stream;
   }

   File getSource() {
      return this.source;
   }
}
