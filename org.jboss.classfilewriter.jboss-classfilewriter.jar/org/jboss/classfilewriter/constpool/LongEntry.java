package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class LongEntry extends ConstPoolEntry {
   private final long value;

   public LongEntry(long value) {
      this.value = value;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.LONG;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeLong(this.value);
   }
}
