package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AffiliateMember;

public class AffiliateMemberBuilder extends AbstractSAMLObjectBuilder {
   public AffiliateMember buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliateMember", "md");
   }

   public AffiliateMember buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AffiliateMemberImpl(namespaceURI, localName, namespacePrefix);
   }
}
