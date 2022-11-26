package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509IssuerSerialBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509IssuerSerial buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509IssuerSerialImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509IssuerSerial buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial", "ds");
   }
}
