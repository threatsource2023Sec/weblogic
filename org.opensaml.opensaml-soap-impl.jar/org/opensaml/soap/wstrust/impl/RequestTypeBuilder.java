package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestType;

public class RequestTypeBuilder extends AbstractWSTrustObjectBuilder {
   public RequestType buildObject() {
      return (RequestType)this.buildObject(RequestType.ELEMENT_NAME);
   }

   public RequestType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
