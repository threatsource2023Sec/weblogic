package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class InterfaceMethodRefEntry extends ConstPoolEntry {
   private final int classIndex;
   private final int nameAndTypeIndex;

   public InterfaceMethodRefEntry(int classIndex, int nameAndTypeIndex) {
      this.classIndex = classIndex;
      this.nameAndTypeIndex = nameAndTypeIndex;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.INTERFACE_METHODREF;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeShort(this.classIndex);
      stream.writeShort(this.nameAndTypeIndex);
   }
}
