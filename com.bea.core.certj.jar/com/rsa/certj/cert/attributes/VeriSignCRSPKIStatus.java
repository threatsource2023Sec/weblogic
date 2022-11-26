package com.rsa.certj.cert.attributes;

/** @deprecated */
public class VeriSignCRSPKIStatus extends IntegerPrintableStringAttr {
   /** @deprecated */
   public VeriSignCRSPKIStatus() {
      super(7, "VeriSignCRSPKIStatus");
   }

   /** @deprecated */
   public VeriSignCRSPKIStatus(int var1) {
      this();
      this.setValue(var1);
   }

   /** @deprecated */
   public void setPKIStatus(int var1) {
      this.setValue(var1);
   }

   /** @deprecated */
   public int getPKIStatus() {
      return this.getValue();
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      VeriSignCRSPKIStatus var1 = new VeriSignCRSPKIStatus();
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof VeriSignCRSPKIStatus && super.equals(var1);
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + super.hashCode();
   }
}
