package com.rsa.certj.cert.attributes;

/** @deprecated */
public class VeriSignCRSMessageType extends IntegerPrintableStringAttr {
   /** @deprecated */
   public VeriSignCRSMessageType() {
      super(6, "VeriSignCRSMessageType");
   }

   /** @deprecated */
   public VeriSignCRSMessageType(int var1) {
      this();
      this.setValue(var1);
   }

   /** @deprecated */
   public void setMessageType(int var1) {
      this.setValue(var1);
   }

   /** @deprecated */
   public int getMessageType() {
      return this.getValue();
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      VeriSignCRSMessageType var1 = new VeriSignCRSMessageType();
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof VeriSignCRSMessageType && super.equals(var1);
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + super.hashCode();
   }
}
