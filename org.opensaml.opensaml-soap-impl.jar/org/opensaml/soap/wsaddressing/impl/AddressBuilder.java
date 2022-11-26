package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.Address;

public class AddressBuilder extends AbstractWSAddressingObjectBuilder {
   public Address buildObject() {
      return (Address)this.buildObject(Address.ELEMENT_NAME);
   }

   public Address buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AddressImpl(namespaceURI, localName, namespacePrefix);
   }
}
