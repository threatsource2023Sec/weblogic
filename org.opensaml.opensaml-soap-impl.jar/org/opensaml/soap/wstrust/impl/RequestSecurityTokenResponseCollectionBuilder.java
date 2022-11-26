package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestSecurityTokenResponseCollection;

public class RequestSecurityTokenResponseCollectionBuilder extends AbstractWSTrustObjectBuilder {
   public RequestSecurityTokenResponseCollection buildObject() {
      return (RequestSecurityTokenResponseCollection)this.buildObject(RequestSecurityTokenResponseCollection.ELEMENT_NAME);
   }

   public RequestSecurityTokenResponseCollection buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestSecurityTokenResponseCollectionImpl(namespaceURI, localName, namespacePrefix);
   }
}
