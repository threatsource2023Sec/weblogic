package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SessionIndex;

public class SessionIndexBuilder extends AbstractSAMLObjectBuilder {
   public SessionIndex buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "SessionIndex", "saml2p");
   }

   public SessionIndex buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SessionIndexImpl(namespaceURI, localName, namespacePrefix);
   }
}
