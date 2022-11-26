package org.mozilla.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

class ClassFileField {
   private short itsNameIndex;
   private short itsTypeIndex;
   private short itsFlags;
   private short[] itsAttr;

   ClassFileField(short var1, short var2, short var3) {
      this.itsNameIndex = var1;
      this.itsTypeIndex = var2;
      this.itsFlags = var3;
   }

   ClassFileField(short var1, short var2, short var3, short[] var4) {
      this.itsNameIndex = var1;
      this.itsTypeIndex = var2;
      this.itsFlags = var3;
      this.itsAttr = var4;
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.itsFlags);
      var1.writeShort(this.itsNameIndex);
      var1.writeShort(this.itsTypeIndex);
      if (this.itsAttr == null) {
         var1.writeShort(0);
      } else {
         var1.writeShort(1);
         var1.writeShort(this.itsAttr[0]);
         var1.writeShort(this.itsAttr[1]);
         var1.writeShort(this.itsAttr[2]);
         var1.writeShort(this.itsAttr[3]);
      }

   }
}
