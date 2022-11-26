package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.PublicationPath;

public class PublicationPathBuilder extends AbstractSAMLObjectBuilder {
   public PublicationPath buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPath", "mdrpi");
   }

   public PublicationPath buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PublicationPathImpl(namespaceURI, localName, namespacePrefix);
   }
}
