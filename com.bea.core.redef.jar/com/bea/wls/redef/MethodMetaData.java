package com.bea.wls.redef;

public class MethodMetaData extends MemberMetaData {
   private final ClassMetaData _cls;
   private final String _returnType;
   private final String[] _paramTypes;
   private OverrideType _override;

   MethodMetaData(ClassMetaData cls, String name, String returnType, String[] paramTypes, String descriptor, int access) {
      super(name, descriptor, access);
      this._override = OverrideType.NONE;
      this._cls = cls;
      this._returnType = returnType;
      this._paramTypes = paramTypes;
   }

   public String getReturnType() {
      return this._returnType;
   }

   public String[] getParameterTypes() {
      return this._paramTypes;
   }

   public OverrideType getOverride() {
      return this._override;
   }

   public void setOverride(OverrideType override) {
      this._override = override;
   }
}
