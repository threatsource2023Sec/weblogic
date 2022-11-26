package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestSecurityTokenCollection;

public class RequestSecurityTokenCollectionBuilder extends AbstractWSTrustObjectBuilder {
   public RequestSecurityTokenCollection buildObject() {
      return (RequestSecurityTokenCollection)this.buildObject(RequestSecurityTokenCollection.ELEMENT_NAME);
   }

   public RequestSecurityTokenCollection buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestSecurityTokenCollectionImpl(namespaceURI, localName, namespacePrefix);
   }
}
