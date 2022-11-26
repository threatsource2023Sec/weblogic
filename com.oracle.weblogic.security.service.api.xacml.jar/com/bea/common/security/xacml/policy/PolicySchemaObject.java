package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.SchemaObject;

public abstract class PolicySchemaObject extends SchemaObject {
   public static final String NAMESPACE = "urn:oasis:names:tc:xacml:2.0:policy:schema:os";
   public static final String NAMESPACE_PREFIX = "xacml";

   public String getNamespace() {
      return "urn:oasis:names:tc:xacml:2.0:policy:schema:os";
   }

   public String getDesiredNamespacePrefix() {
      return "xacml";
   }
}
