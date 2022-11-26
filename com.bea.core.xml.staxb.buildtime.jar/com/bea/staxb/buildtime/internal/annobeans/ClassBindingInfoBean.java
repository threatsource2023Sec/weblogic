package com.bea.staxb.buildtime.internal.annobeans;

import com.bea.util.annogen.override.internal.AnnoBeanBase;

public class ClassBindingInfoBean extends AnnoBeanBase {
   public static final String ANNOBEAN_FOR = "com.bea.staxb.buildtime.annotations.ClassBindingInfo";
   private TargetSchemaTypeBean _schemaType;
   private boolean _isIgnoreSuperclass;
   private boolean _isExclude;

   public TargetSchemaTypeBean schemaType() {
      return this._schemaType;
   }

   public void set_schemaType(TargetSchemaTypeBean var1) {
      this._schemaType = var1;
   }

   public boolean isIgnoreSuperclass() {
      return this._isIgnoreSuperclass;
   }

   public void set_isIgnoreSuperclass(boolean var1) {
      this._isIgnoreSuperclass = var1;
   }

   public boolean isExclude() {
      return this._isExclude;
   }

   public void set_isExclude(boolean var1) {
      this._isExclude = var1;
   }
}
