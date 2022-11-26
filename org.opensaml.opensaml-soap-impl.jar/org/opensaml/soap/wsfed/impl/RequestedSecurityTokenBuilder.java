package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsfed.RequestedSecurityToken;
import org.opensaml.soap.wsfed.WSFedObjectBuilder;

public class RequestedSecurityTokenBuilder extends AbstractXMLObjectBuilder implements WSFedObjectBuilder {
   public RequestedSecurityToken buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityToken", "wst");
   }

   public RequestedSecurityToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedSecurityTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
