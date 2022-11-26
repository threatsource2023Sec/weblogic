package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Issuer;

public class IssuerBuilder extends AbstractWSTrustObjectBuilder {
   public Issuer buildObject() {
      return (Issuer)this.buildObject(Issuer.ELEMENT_NAME);
   }

   public Issuer buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IssuerImpl(namespaceURI, localName, namespacePrefix);
   }
}
