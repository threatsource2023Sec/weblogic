package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509SerialNumber;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509SerialNumberBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509SerialNumber buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509SerialNumberImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509SerialNumber buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
   }
}
