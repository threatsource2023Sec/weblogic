package com.bea.staxb.buildtime.internal.annobeans;

import com.bea.util.annogen.override.internal.AnnoBeanBase;

public class MethodBindingInfoBean extends AnnoBeanBase {
   public static final String ANNOBEAN_FOR = "com.bea.staxb.buildtime.annotations.MethodBindingInfo";
   private TargetSchemaPropertyBean _schemaProperty;
   private String _defaultCheckerFor;
   private String _factoryFor;
   private boolean _isExclude;
   private String _asTypeNamed;
   private String _asComponentTypeNamed;

   public TargetSchemaPropertyBean schemaProperty() {
      return this._schemaProperty;
   }

   public void set_schemaProperty(TargetSchemaPropertyBean var1) {
      this._schemaProperty = var1;
   }

   public String defaultCheckerFor() {
      return this._defaultCheckerFor;
   }

   public void set_defaultCheckerFor(String var1) {
      this._defaultCheckerFor = var1;
   }

   public String factoryFor() {
      return this._factoryFor;
   }

   public void set_factoryFor(String var1) {
      this._factoryFor = var1;
   }

   public boolean isExclude() {
      return this._isExclude;
   }

   public void set_isExclude(boolean var1) {
      this._isExclude = var1;
   }

   public String asTypeNamed() {
      return this._asTypeNamed;
   }

   public void set_asTypeNamed(String var1) {
      this._asTypeNamed = var1;
   }

   public String asComponentTypeNamed() {
      return this._asComponentTypeNamed;
   }

   public void set_asComponentTypeNamed(String var1) {
      this._asComponentTypeNamed = var1;
   }
}
