package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2alg.SigningMethod;

public class SigningMethodBuilder extends AbstractSAMLObjectBuilder {
   public SigningMethod buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethod", "alg");
   }

   public SigningMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SigningMethodImpl(namespaceURI, localName, namespacePrefix);
   }
}
