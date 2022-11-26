package org.mozilla.javascript;

class InterpreterData {
   static final int INITIAL_MAX_ICODE_LENGTH = 1024;
   static final int INITIAL_STRINGTABLE_SIZE = 64;
   static final int INITIAL_NUMBERTABLE_SIZE = 64;
   String itsName;
   String itsSource;
   String itsSourceFile;
   boolean itsNeedsActivation;
   boolean itsFromEvalCode;
   boolean itsUseDynamicScope;
   boolean itsCheckThis;
   byte itsFunctionType;
   String[] itsStringTable;
   int itsStringTableIndex;
   double[] itsNumberTable;
   int itsNumberTableIndex;
   InterpretedFunction[] itsNestedFunctions;
   Object[] itsRegExpLiterals;
   byte[] itsICode;
   int itsICodeTop;
   int itsMaxLocals;
   int itsMaxArgs;
   int itsMaxStack;
   int itsMaxTryDepth;
   UintMap itsLineNumberTable;
   Object securityDomain;

   InterpreterData(int var1, int var2, int var3, Object var4, boolean var5, boolean var6) {
      this.itsICodeTop = var1 == 0 ? 1024 : var1 * 2;
      this.itsICode = new byte[this.itsICodeTop];
      this.itsStringTable = new String[var2 == 0 ? 64 : var2 * 2];
      this.itsNumberTable = new double[var3 == 0 ? 64 : var3 * 2];
      this.itsUseDynamicScope = var5;
      this.itsCheckThis = var6;
      if (var4 == null) {
         Context.checkSecurityDomainRequired();
      }

      this.securityDomain = var4;
   }

   private int getOffset(int var1) {
      int var2 = this.itsLineNumberTable.getInt(var1, -1);
      return var2 >= 0 && var2 <= this.itsICode.length ? var2 : -1;
   }

   public boolean placeBreakpoint(int var1) {
      int var2 = this.getOffset(var1);
      if (var2 == -1 || this.itsICode[var2] != -109 && this.itsICode[var2] != -107) {
         return false;
      } else {
         this.itsICode[var2] = -107;
         return true;
      }
   }

   public boolean removeBreakpoint(int var1) {
      int var2 = this.getOffset(var1);
      if (var2 != -1 && this.itsICode[var2] == -107) {
         this.itsICode[var2] = -109;
         return true;
      } else {
         return false;
      }
   }
}
