package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestKET;

public class RequestKETBuilder extends AbstractWSTrustObjectBuilder {
   public RequestKET buildObject() {
      return (RequestKET)this.buildObject(RequestKET.ELEMENT_NAME);
   }

   public RequestKET buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestKETImpl(namespaceURI, localName, namespacePrefix);
   }
}
