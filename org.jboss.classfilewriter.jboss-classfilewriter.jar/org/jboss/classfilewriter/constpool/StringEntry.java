package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class StringEntry extends ConstPoolEntry {
   private final int utf8Index;

   public StringEntry(int utf8Index) {
      this.utf8Index = utf8Index;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.STRING;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeShort(this.utf8Index);
   }
}
