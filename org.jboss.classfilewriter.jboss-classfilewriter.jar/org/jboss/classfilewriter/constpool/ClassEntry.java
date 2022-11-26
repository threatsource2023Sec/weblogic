package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClassEntry extends ConstPoolEntry {
   private final int utf8Location;

   public ClassEntry(int utf8Location) {
      this.utf8Location = utf8Location;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.CLASS;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeShort(this.utf8Location);
   }
}
