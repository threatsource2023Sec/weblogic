package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Created;

public class CreatedBuilder extends AbstractWSSecurityObjectBuilder {
   public Created buildObject() {
      return (Created)this.buildObject(Created.ELEMENT_NAME);
   }

   public Created buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CreatedImpl(namespaceURI, localName, namespacePrefix);
   }
}
