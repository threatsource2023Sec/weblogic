package com.bea.security.providers.xacml.store.ldap;

class WriterFactory {
   public static ConvertedEntlWriter create(boolean isLdift) {
      return (ConvertedEntlWriter)(isLdift ? new XACMLLdiftWriter() : new XACMLPolicyWriter());
   }
}
