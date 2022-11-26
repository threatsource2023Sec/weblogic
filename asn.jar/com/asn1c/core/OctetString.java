package com.asn1c.core;

public class OctetString extends UnitString {
   static final long serialVersionUID = 8876499961256550409L;

   public OctetString() {
      super(8);
   }

   public OctetString(int var1) {
      super(8, var1);
   }

   public OctetString(byte[] var1) {
      super(8, (byte[])var1);
   }

   public OctetString(byte[] var1, int var2, int var3) {
      super(8, var1, var2, var3);
   }

   public OctetString(UnitString var1) {
      super(8, (UnitString)var1);
   }
}
