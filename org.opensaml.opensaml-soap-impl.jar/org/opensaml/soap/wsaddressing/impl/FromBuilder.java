package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.From;

public class FromBuilder extends AbstractWSAddressingObjectBuilder {
   public From buildObject() {
      return (From)this.buildObject(From.ELEMENT_NAME);
   }

   public From buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FromImpl(namespaceURI, localName, namespacePrefix);
   }
}
