package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class NameAndTypeEntry extends ConstPoolEntry {
   private final int nameIndex;
   private final int descriptorIndex;

   public NameAndTypeEntry(int nameIndex, int descriptorIndex) {
      this.nameIndex = nameIndex;
      this.descriptorIndex = descriptorIndex;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.NAME_AND_TYPE;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeShort(this.nameIndex);
      stream.writeShort(this.descriptorIndex);
   }
}
