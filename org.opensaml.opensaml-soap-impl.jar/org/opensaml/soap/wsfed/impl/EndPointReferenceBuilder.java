package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsfed.EndPointReference;
import org.opensaml.soap.wsfed.WSFedObjectBuilder;

public class EndPointReferenceBuilder extends AbstractXMLObjectBuilder implements WSFedObjectBuilder {
   public EndPointReference buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndPointReference", "wsa");
   }

   public EndPointReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EndPointReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
