package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.Envelope;

public class EnvelopeBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public Envelope buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap11");
   }

   public Envelope buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EnvelopeImpl(namespaceURI, localName, namespacePrefix);
   }
}
