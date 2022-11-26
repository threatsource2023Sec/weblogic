package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.RequesterID;

public class RequesterIDBuilder extends AbstractSAMLObjectBuilder {
   public RequesterID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "RequesterID", "saml2p");
   }

   public RequesterID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequesterIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
