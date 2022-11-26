package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Embedded;

public class EmbeddedBuilder extends AbstractWSSecurityObjectBuilder {
   public Embedded buildObject() {
      return (Embedded)this.buildObject(Embedded.ELEMENT_NAME);
   }

   public Embedded buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EmbeddedImpl(namespaceURI, localName, namespacePrefix);
   }
}
