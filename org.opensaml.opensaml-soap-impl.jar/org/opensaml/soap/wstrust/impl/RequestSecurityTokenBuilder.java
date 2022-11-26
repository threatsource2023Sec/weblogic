package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestSecurityToken;

public class RequestSecurityTokenBuilder extends AbstractWSTrustObjectBuilder {
   public RequestSecurityToken buildObject() {
      return (RequestSecurityToken)this.buildObject(RequestSecurityToken.ELEMENT_NAME);
   }

   public RequestSecurityToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestSecurityTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
