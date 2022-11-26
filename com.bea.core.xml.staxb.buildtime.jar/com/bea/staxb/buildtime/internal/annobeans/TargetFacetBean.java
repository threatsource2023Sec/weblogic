package com.bea.staxb.buildtime.internal.annobeans;

import com.bea.util.annogen.override.internal.AnnoBeanBase;

public class TargetFacetBean extends AnnoBeanBase {
   public static final String ANNOBEAN_FOR = "com.bea.staxb.buildtime.annotations.TargetFacet";
   private int _typeNum;
   private String _value;

   public int typeNum() {
      return this._typeNum;
   }

   public void set_typeNum(int var1) {
      this._typeNum = var1;
   }

   public String value() {
      return this._value;
   }

   public void set_value(String var1) {
      this._value = var1;
   }
}
