package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class FieldRefEntry extends ConstPoolEntry {
   private final int classIndex;
   private final int nameAndTypeIndex;

   public FieldRefEntry(int classIndex, int nameAndTypeIndex) {
      this.classIndex = classIndex;
      this.nameAndTypeIndex = nameAndTypeIndex;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.FIELDREF;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeShort(this.classIndex);
      stream.writeShort(this.nameAndTypeIndex);
   }
}
