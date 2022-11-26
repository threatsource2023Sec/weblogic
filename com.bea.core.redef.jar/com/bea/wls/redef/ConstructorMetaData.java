package com.bea.wls.redef;

public class ConstructorMetaData extends MemberMetaData {
   private final String[] _paramTypes;

   ConstructorMetaData(String[] paramTypes, String descriptor, int access) {
      super("<init>", descriptor, access);
      this._paramTypes = paramTypes;
   }

   public String[] getParameterTypes() {
      return this._paramTypes;
   }
}
