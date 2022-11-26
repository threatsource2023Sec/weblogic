package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.Publication;

public class PublicationBuilder extends AbstractSAMLObjectBuilder {
   public Publication buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "Publication", "mdrpi");
   }

   public Publication buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PublicationImpl(namespaceURI, localName, namespacePrefix);
   }
}
