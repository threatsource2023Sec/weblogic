package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.DoNotCacheCondition;

public class DoNotCacheConditionBuilder extends AbstractSAMLObjectBuilder {
   public DoNotCacheCondition buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition", "saml1");
   }

   public DoNotCacheCondition buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DoNotCacheConditionImpl(namespaceURI, localName, namespacePrefix);
   }
}
