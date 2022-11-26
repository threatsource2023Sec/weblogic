package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsfed.RequestSecurityTokenResponse;
import org.opensaml.soap.wsfed.WSFedObjectBuilder;

public class RequestSecurityTokenResponseBuilder extends AbstractXMLObjectBuilder implements WSFedObjectBuilder {
   public RequestSecurityTokenResponse buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponse", "wst");
   }

   public RequestSecurityTokenResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestSecurityTokenResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
