package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2alg.DigestMethod;

public class DigestMethodBuilder extends AbstractSAMLObjectBuilder {
   public DigestMethod buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethod", "alg");
   }

   public DigestMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DigestMethodImpl(namespaceURI, localName, namespacePrefix);
   }
}
