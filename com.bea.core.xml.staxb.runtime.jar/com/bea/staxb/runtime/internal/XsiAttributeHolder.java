package com.bea.staxb.runtime.internal;

import javax.xml.namespace.QName;

final class XsiAttributeHolder {
   boolean hasXsiNil;
   QName xsiType;
   String schemaLocation;
   String noNamespaceSchemaLocation;

   void reset() {
      this.hasXsiNil = false;
      this.xsiType = null;
      this.schemaLocation = null;
      this.noNamespaceSchemaLocation = null;
   }
}
