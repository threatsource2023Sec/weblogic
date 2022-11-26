package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.LocalVariable;

public class OptLocalVariable extends LocalVariable {
   private short itsJRegister = -1;
   private boolean itsLiveAcrossCall;
   private boolean itsIsNumber;
   private TypeEvent itsTypeUnion;
   private int initPC;

   public OptLocalVariable(String var1, boolean var2) {
      super(var1, var2);
      int var3 = var2 ? 3 : 0;
      this.itsTypeUnion = new TypeEvent(var3);
   }

   public void assignJRegister(short var1) {
      this.itsJRegister = var1;
   }

   public boolean assignType(int var1) {
      return this.itsTypeUnion.add(var1);
   }

   public void clearLiveAcrossCall() {
      this.itsLiveAcrossCall = false;
   }

   public short getJRegister() {
      return this.itsJRegister;
   }

   public int getStartPC() {
      return this.initPC;
   }

   public int getTypeUnion() {
      return this.itsTypeUnion.getEvent();
   }

   public boolean isLiveAcrossCall() {
      return this.itsLiveAcrossCall;
   }

   public boolean isNumber() {
      return this.itsIsNumber;
   }

   public void markLiveAcrossCall() {
      this.itsLiveAcrossCall = true;
   }

   public void setIsNumber() {
      this.itsIsNumber = true;
   }

   public void setStartPC(int var1) {
      this.initPC = var1;
   }

   public String toString() {
      return "LocalVariable : '" + this.getName() + "', index = " + this.getIndex() + ", LiveAcrossCall = " + this.itsLiveAcrossCall + ", isNumber = " + this.itsIsNumber + ", isParameter = " + this.isParameter() + ", JRegister = " + this.itsJRegister;
   }
}
