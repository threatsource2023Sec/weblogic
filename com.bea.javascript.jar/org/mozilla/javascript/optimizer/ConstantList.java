package org.mozilla.javascript.optimizer;

class ConstantList {
   ConstantDude[] itsList = new ConstantDude[128];
   int itsTop;

   int addConstant(String var1, String var2, Number var3, boolean var4) {
      long var5 = var3.longValue();
      double var7 = var3.doubleValue();

      for(int var9 = 0; var9 < this.itsTop; ++var9) {
         if (var2.equals(this.itsList[var9].itsSignature)) {
            if (this.itsList[var9].itsIsInteger) {
               if (var4 && var5 == this.itsList[var9].itsLValue) {
                  return var9;
               }
            } else if (!var4 && var7 == this.itsList[var9].itsDValue) {
               return var9;
            }
         }
      }

      if (this.itsTop == this.itsList.length) {
         ConstantDude[] var10 = new ConstantDude[this.itsList.length * 2];
         System.arraycopy(this.itsList, 0, var10, 0, this.itsList.length);
         this.itsList = var10;
      }

      if (var4) {
         this.itsList[this.itsTop] = new ConstantDude(var1, var2, var5);
      } else {
         this.itsList[this.itsTop] = new ConstantDude(var1, var2, var7);
      }

      return this.itsTop++;
   }
}
