package org.mozilla.classfile;

import org.mozilla.javascript.Label;

class ExceptionTableEntry {
   private int itsStartLabel;
   private int itsEndLabel;
   private int itsHandlerLabel;
   private short itsCatchType;

   ExceptionTableEntry(int var1, int var2, int var3, short var4) {
      this.itsStartLabel = var1;
      this.itsEndLabel = var2;
      this.itsHandlerLabel = var3;
      this.itsCatchType = var4;
   }

   short getCatchType() {
      return this.itsCatchType;
   }

   short getEndPC(Label[] var1) {
      short var2 = var1[this.itsEndLabel & Integer.MAX_VALUE].getPC();
      if (var2 == -1) {
         throw new RuntimeException("end label not defined");
      } else {
         return var2;
      }
   }

   short getHandlerPC(Label[] var1) {
      short var2 = var1[this.itsHandlerLabel & Integer.MAX_VALUE].getPC();
      if (var2 == -1) {
         throw new RuntimeException("handler label not defined");
      } else {
         return var2;
      }
   }

   short getStartPC(Label[] var1) {
      short var2 = var1[this.itsStartLabel & Integer.MAX_VALUE].getPC();
      if (var2 == -1) {
         throw new RuntimeException("start label not defined");
      } else {
         return var2;
      }
   }
}
