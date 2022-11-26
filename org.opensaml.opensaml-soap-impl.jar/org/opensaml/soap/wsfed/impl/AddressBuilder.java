package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsfed.Address;
import org.opensaml.soap.wsfed.WSFedObjectBuilder;

public class AddressBuilder extends AbstractXMLObjectBuilder implements WSFedObjectBuilder {
   public final Address buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Address", "wsa");
   }

   public Address buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AddressImpl(namespaceURI, localName, namespacePrefix);
   }
}
