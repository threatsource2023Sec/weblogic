package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;

public class RequestSecurityTokenResponseBuilder extends AbstractWSTrustObjectBuilder {
   public RequestSecurityTokenResponse buildObject() {
      return (RequestSecurityTokenResponse)this.buildObject(RequestSecurityTokenResponse.ELEMENT_NAME);
   }

   public RequestSecurityTokenResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestSecurityTokenResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
