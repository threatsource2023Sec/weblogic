package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;

public class ManageNameIDServiceBuilder extends AbstractSAMLObjectBuilder {
   public ManageNameIDService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ManageNameIDService", "md");
   }

   public ManageNameIDService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ManageNameIDServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
