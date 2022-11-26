package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.RelayState;

public class RelayStateBuilder extends AbstractSAMLObjectBuilder {
   public RelayState buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayState", "ecp");
   }

   public RelayState buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RelayStateImpl(namespaceURI, localName, namespacePrefix);
   }
}
