package org.mozilla.javascript.optimizer;

class ConstantDude {
   String itsWrapperType;
   String itsSignature;
   long itsLValue;
   double itsDValue;
   boolean itsIsInteger;

   ConstantDude(String var1, String var2, double var3) {
      this.itsWrapperType = var1;
      this.itsSignature = var2;
      this.itsIsInteger = false;
      this.itsDValue = var3;
   }

   ConstantDude(String var1, String var2, long var3) {
      this.itsWrapperType = var1;
      this.itsSignature = var2;
      this.itsIsInteger = true;
      this.itsLValue = var3;
   }
}
