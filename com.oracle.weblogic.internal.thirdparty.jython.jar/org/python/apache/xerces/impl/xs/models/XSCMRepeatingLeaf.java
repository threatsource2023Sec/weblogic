package org.python.apache.xerces.impl.xs.models;

public final class XSCMRepeatingLeaf extends XSCMLeaf {
   private final int fMinOccurs;
   private final int fMaxOccurs;

   public XSCMRepeatingLeaf(int var1, Object var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var5, var6);
      this.fMinOccurs = var3;
      this.fMaxOccurs = var4;
   }

   final int getMinOccurs() {
      return this.fMinOccurs;
   }

   final int getMaxOccurs() {
      return this.fMaxOccurs;
   }
}
