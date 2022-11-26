package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;

public class AudienceRestrictionConditionBuilder extends AbstractSAMLObjectBuilder {
   public AudienceRestrictionCondition buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition", "saml1");
   }

   public AudienceRestrictionCondition buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AudienceRestrictionConditionImpl(namespaceURI, localName, namespacePrefix);
   }
}
