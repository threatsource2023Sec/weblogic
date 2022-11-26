package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;

public class RegistrationInfoBuilder extends AbstractSAMLObjectBuilder {
   public RegistrationInfo buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfo", "mdrpi");
   }

   public RegistrationInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RegistrationInfoImpl(namespaceURI, localName, namespacePrefix);
   }
}
