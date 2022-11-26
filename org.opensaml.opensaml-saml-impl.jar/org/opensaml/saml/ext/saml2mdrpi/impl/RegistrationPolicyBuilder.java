package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;

public class RegistrationPolicyBuilder extends AbstractSAMLObjectBuilder {
   public RegistrationPolicy buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationPolicy", "mdrpi");
   }

   public RegistrationPolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RegistrationPolicyImpl(namespaceURI, localName, namespacePrefix);
   }
}
