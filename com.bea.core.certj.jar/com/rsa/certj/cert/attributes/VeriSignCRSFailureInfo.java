package com.rsa.certj.cert.attributes;

/** @deprecated */
public class VeriSignCRSFailureInfo extends IntegerPrintableStringAttr {
   /** @deprecated */
   public VeriSignCRSFailureInfo() {
      super(8, "VeriSignCRSFailureInfo");
   }

   /** @deprecated */
   public VeriSignCRSFailureInfo(int var1) {
      this();
      this.setValue(var1);
   }

   /** @deprecated */
   public void setFailureInfo(int var1) {
      this.setValue(var1);
   }

   /** @deprecated */
   public int getFailureInfo() {
      return this.getValue();
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      VeriSignCRSFailureInfo var1 = new VeriSignCRSFailureInfo();
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof VeriSignCRSFailureInfo && super.equals(var1);
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + super.hashCode();
   }
}
