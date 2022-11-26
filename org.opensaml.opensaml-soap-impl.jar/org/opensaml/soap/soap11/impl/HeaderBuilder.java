package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.Header;

public class HeaderBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public Header buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/soap/envelope/", "Header", "soap11");
   }

   public Header buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new HeaderImpl(namespaceURI, localName, namespacePrefix);
   }
}
