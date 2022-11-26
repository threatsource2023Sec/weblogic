package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.Address;

public class AddressImpl extends AttributedURIImpl implements Address {
   public AddressImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
