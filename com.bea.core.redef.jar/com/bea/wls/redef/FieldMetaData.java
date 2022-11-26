package com.bea.wls.redef;

public class FieldMetaData extends MemberMetaData {
   private final String _declarer;
   private final String _type;

   FieldMetaData(String declarer, String name, String descriptor, String type, int access) {
      super(name, descriptor, access);
      this._declarer = declarer;
      this._type = type;
   }

   public String getDeclarer() {
      return this._declarer;
   }

   public String getType() {
      return this._type;
   }
}
