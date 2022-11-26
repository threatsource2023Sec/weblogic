package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class FloatEntry extends ConstPoolEntry {
   private final float value;

   public FloatEntry(float value) {
      this.value = value;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.FLOAT;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeFloat(this.value);
   }
}
