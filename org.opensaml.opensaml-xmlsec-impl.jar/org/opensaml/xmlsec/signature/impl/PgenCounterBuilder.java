package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PgenCounterBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public PgenCounter buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
   }

   public PgenCounter buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PgenCounter", "ds");
   }
}
