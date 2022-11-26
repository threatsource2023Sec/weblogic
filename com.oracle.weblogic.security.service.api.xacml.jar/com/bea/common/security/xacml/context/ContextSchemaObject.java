package com.bea.common.security.xacml.context;

import com.bea.common.security.xacml.SchemaObject;

public abstract class ContextSchemaObject extends SchemaObject {
   public static final String NAMESPACE = "urn:oasis:names:tc:xacml:2.0:context:schema:os";
   public static final String NAMESPACE_PREFIX = "xacml-context";

   public String getNamespace() {
      return "urn:oasis:names:tc:xacml:2.0:context:schema:os";
   }

   public String getDesiredNamespacePrefix() {
      return "xacml-context";
   }
}
