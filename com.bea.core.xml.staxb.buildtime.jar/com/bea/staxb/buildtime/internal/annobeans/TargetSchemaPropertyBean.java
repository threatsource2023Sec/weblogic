package com.bea.staxb.buildtime.internal.annobeans;

import com.bea.util.annogen.override.internal.AnnoBeanBase;

public class TargetSchemaPropertyBean extends AnnoBeanBase {
   public static final String ANNOBEAN_FOR = "com.bea.staxb.buildtime.annotations.TargetSchemaProperty";
   private String _localName;
   private String _namespaceUri;
   private boolean _isAttribute;
   private boolean _isRequired;
   private boolean _isNillable;
   private boolean _isFormQualified;
   private String _defaultValue;
   private TargetFacetBean[] _facets;

   public String localName() {
      return this._localName;
   }

   public void set_localName(String var1) {
      this._localName = var1;
   }

   public String namespaceUri() {
      return this._namespaceUri;
   }

   public void set_namespaceUri(String var1) {
      this._namespaceUri = var1;
   }

   public boolean isAttribute() {
      return this._isAttribute;
   }

   public void set_isAttribute(boolean var1) {
      this._isAttribute = var1;
   }

   public boolean isRequired() {
      return this._isRequired;
   }

   public void set_isRequired(boolean var1) {
      this._isRequired = var1;
   }

   public boolean isNillable() {
      return this._isNillable;
   }

   public void set_isNillable(boolean var1) {
      this._isNillable = var1;
   }

   public boolean isFormQualified() {
      return this._isFormQualified;
   }

   public void set_isFormQualified(boolean var1) {
      this._isFormQualified = var1;
   }

   public String defaultValue() {
      return this._defaultValue;
   }

   public void set_defaultValue(String var1) {
      this._defaultValue = var1;
   }

   public TargetFacetBean[] facets() {
      return this._facets;
   }

   public void set_facets(TargetFacetBean[] var1) {
      this._facets = var1;
   }
}
