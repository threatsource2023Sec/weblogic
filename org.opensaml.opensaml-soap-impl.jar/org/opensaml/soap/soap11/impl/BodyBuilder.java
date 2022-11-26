package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.Body;

public class BodyBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public Body buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/soap/envelope/", "Body", "soap11");
   }

   public Body buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new BodyImpl(namespaceURI, localName, namespacePrefix);
   }
}
