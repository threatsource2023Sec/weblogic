package org.mozilla.javascript;

public class LocalVariable {
   private String itsName;
   private int itsIndex = -1;
   private boolean itsIsParameter;

   public LocalVariable(String var1, boolean var2) {
      this.itsName = var1;
      this.itsIsParameter = var2;
   }

   public int getIndex() {
      return this.itsIndex;
   }

   public short getJRegister() {
      return -1;
   }

   public String getName() {
      return this.itsName;
   }

   public int getStartPC() {
      return -1;
   }

   public boolean isNumber() {
      return false;
   }

   public boolean isParameter() {
      return this.itsIsParameter;
   }

   public void setIndex(int var1) {
      this.itsIndex = var1;
   }

   public void setIsParameter() {
      this.itsIsParameter = true;
   }
}
