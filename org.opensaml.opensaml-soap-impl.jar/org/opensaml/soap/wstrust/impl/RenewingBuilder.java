package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Renewing;

public class RenewingBuilder extends AbstractWSTrustObjectBuilder {
   public Renewing buildObject() {
      return (Renewing)this.buildObject(Renewing.ELEMENT_NAME);
   }

   public Renewing buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RenewingImpl(namespaceURI, localName, namespacePrefix);
   }
}
