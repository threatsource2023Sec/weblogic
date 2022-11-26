package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.Fault;

public class FaultBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public Fault buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/soap/envelope/", "Fault", "soap11");
   }

   public Fault buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FaultImpl(namespaceURI, localName, namespacePrefix);
   }
}
