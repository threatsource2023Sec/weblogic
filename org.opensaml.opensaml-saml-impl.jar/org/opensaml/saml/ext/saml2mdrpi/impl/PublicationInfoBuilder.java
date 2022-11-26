package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.PublicationInfo;

public class PublicationInfoBuilder extends AbstractSAMLObjectBuilder {
   public PublicationInfo buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationInfo", "mdrpi");
   }

   public PublicationInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PublicationInfoImpl(namespaceURI, localName, namespacePrefix);
   }
}
