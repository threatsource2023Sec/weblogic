package com.bea.staxb.buildtime.internal.annobeans;

import com.bea.util.annogen.override.internal.AnnoBeanBase;

public class TargetTopLevelElementBean extends AnnoBeanBase {
   public static final String ANNOBEAN_FOR = "com.bea.staxb.buildtime.annotations.TargetTopLevelElement";
   private String _localName;
   private String _namespaceUri;

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
}
