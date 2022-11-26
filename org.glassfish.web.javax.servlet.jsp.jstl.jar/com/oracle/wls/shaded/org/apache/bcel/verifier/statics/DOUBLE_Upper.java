package com.oracle.wls.shaded.org.apache.bcel.verifier.statics;

import com.oracle.wls.shaded.org.apache.bcel.generic.Type;

public final class DOUBLE_Upper extends Type {
   private static DOUBLE_Upper singleInstance = new DOUBLE_Upper();

   private DOUBLE_Upper() {
      super((byte)15, "Long_Upper");
   }

   public static DOUBLE_Upper theInstance() {
      return singleInstance;
   }
}
