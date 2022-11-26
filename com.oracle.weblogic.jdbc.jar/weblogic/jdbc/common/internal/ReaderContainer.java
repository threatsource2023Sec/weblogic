package weblogic.jdbc.common.internal;

import java.io.Reader;

public final class ReaderContainer {
   Reader rdr;
   int block_size;

   public ReaderContainer(Reader rdr, int block_size) {
      this.rdr = rdr;
      this.block_size = block_size;
   }
}
