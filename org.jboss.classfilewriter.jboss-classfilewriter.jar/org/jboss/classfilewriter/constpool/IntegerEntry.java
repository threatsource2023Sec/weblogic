package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class IntegerEntry extends ConstPoolEntry {
   private final int value;

   public IntegerEntry(int value) {
      this.value = value;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.INTEGER;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeInt(this.value);
   }
}
