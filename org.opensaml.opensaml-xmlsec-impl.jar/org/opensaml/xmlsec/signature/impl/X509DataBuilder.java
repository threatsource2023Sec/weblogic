package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509DataBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509Data buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509DataImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509Data buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
   }
}
