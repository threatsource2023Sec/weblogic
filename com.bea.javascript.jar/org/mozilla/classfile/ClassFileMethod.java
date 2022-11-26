package org.mozilla.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

class ClassFileMethod {
   private short itsNameIndex;
   private short itsTypeIndex;
   private short itsFlags;
   private byte[] itsCodeAttribute;

   ClassFileMethod(short var1, short var2, short var3) {
      this.itsNameIndex = var1;
      this.itsTypeIndex = var2;
      this.itsFlags = var3;
   }

   void setCodeAttribute(byte[] var1) {
      this.itsCodeAttribute = var1;
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.itsFlags);
      var1.writeShort(this.itsNameIndex);
      var1.writeShort(this.itsTypeIndex);
      var1.writeShort(1);
      var1.write(this.itsCodeAttribute, 0, this.itsCodeAttribute.length);
   }
}
