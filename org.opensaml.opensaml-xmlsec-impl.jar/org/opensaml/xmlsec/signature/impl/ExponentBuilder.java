package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class ExponentBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public Exponent buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ExponentImpl(namespaceURI, localName, namespacePrefix);
   }

   public Exponent buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Exponent", "ds");
   }
}
