package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleEntry extends ConstPoolEntry {
   private final double value;

   public DoubleEntry(double value) {
      this.value = value;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.DOUBLE;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeDouble(this.value);
   }
}
