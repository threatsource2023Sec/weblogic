package weblogic.jdbc.common.internal;

import java.io.InputStream;

public final class InputStreamContainer {
   InputStream is;
   int block_size;

   public InputStreamContainer(InputStream is, int block_size) {
      this.is = is;
      this.block_size = block_size;
   }
}
