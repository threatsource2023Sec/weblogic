package com.bea.security.providers.xacml.store.file;

import com.bea.common.security.xacml.SchemaObject;

abstract class IndexSchemaObject extends SchemaObject {
   public static final String NAMESPACE = "urn:bea:xacml:2.0:index:schema:01";
   public static final String NAMESPACE_PREFIX = "index";

   public String getNamespace() {
      return "urn:bea:xacml:2.0:index:schema:01";
   }

   public String getDesiredNamespacePrefix() {
      return "index";
   }
}
