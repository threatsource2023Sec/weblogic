package com.asn1c.core;

public class BitString extends UnitString {
   static final long serialVersionUID = -3130358595516517618L;

   public BitString() {
      super(1);
   }

   public BitString(UnitString var1) {
      super(1, (UnitString)var1);
   }

   public BitString(int var1) {
      super(1, var1);
   }

   public BitString(byte[] var1) {
      super(1, (byte[])var1);
   }

   public BitString(byte[] var1, int var2, int var3) {
      super(1, var1, var2, var3);
   }
}
