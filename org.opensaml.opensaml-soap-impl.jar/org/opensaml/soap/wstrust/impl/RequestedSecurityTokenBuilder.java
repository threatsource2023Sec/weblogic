package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedSecurityToken;

public class RequestedSecurityTokenBuilder extends AbstractWSTrustObjectBuilder {
   public RequestedSecurityToken buildObject() {
      return (RequestedSecurityToken)this.buildObject(RequestedSecurityToken.ELEMENT_NAME);
   }

   public RequestedSecurityToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedSecurityTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
